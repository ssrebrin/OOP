package ru.nsu.rebrin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import static java.lang.Math.max;

/**
 * Pizzeria class.
 */
public class Pizzeria implements DeliveryQueue {
    private final Object lock = new Object();
    private final AtomicBoolean deliveryFinished = new AtomicBoolean(false);
    private int pizzaCounter;
    private final int warehouseCapacity;
    final List<Integer> queueCook = new ArrayList<>();
    final List<Integer> queueDeliv = new ArrayList<>();
    final List<Thread> cookerThreads = new ArrayList<>();
    final List<Thread> delivererThreads = new ArrayList<>();
    int mx = 0;

    /**
     * Init.
     *
     * @param cookingTime       - List with cookers cooking time
     * @param deliverTime       - List with deliverers time
     * @param warehouseCapacity - Максимальная вместимость склада
     */
    public Pizzeria(List<Integer> cookingTime, List<Integer> deliverTime, int warehouseCapacity) {
        if (cookingTime == null || cookingTime.isEmpty()) {
            throw new IllegalArgumentException("Cooking time list cannot be null or empty");
        }
        if (deliverTime == null || deliverTime.isEmpty()) {
            throw new IllegalArgumentException("Deliver time list cannot be null or empty");
        }
        if (warehouseCapacity <= 0) {
            throw new IllegalArgumentException("Warehouse capacity must be greater than 0");
        }

        this.warehouseCapacity = warehouseCapacity;
        this.pizzaCounter = 1;

        // Запуск поваров
        for (int time : cookingTime) {
            Cooker cooker = new Cooker(time, this);
            Thread cookerThread = new Thread(cooker);
            cookerThreads.add(cookerThread);
            cookerThread.start();
        }

        // Запуск доставщиков
        for (int time : deliverTime) {
            Deliver deliverer = new Deliver(time, this);
            Thread delivererThread = new Thread(deliverer);
            delivererThreads.add(delivererThread);
            mx = max(mx, time);
            delivererThread.start();
        }
    }

    @Override
    public int getFromQueue() throws InterruptedException {
        synchronized (lock) {
            while (isOpen() && queueCook.isEmpty()) {
                lock.wait();
            }
            if (!queueCook.isEmpty()) {
                return queueCook.remove(0);
            }
            return 0;
        }
    }

    @Override
    public void addToQueue(int orderId) {
        synchronized (lock) {
            queueCook.add(orderId);
            lock.notifyAll();
        }
    }

    @Override
    public boolean isDeliveryFinished() {
        return deliveryFinished.get();
    }

    @Override
    public int getFromDeliveryQueue() throws InterruptedException {
        synchronized (lock) {
            while (isOpen() && queueDeliv.isEmpty()) {
                lock.wait();
            }
            if (!queueDeliv.isEmpty()) {
                return queueDeliv.remove(0);
            }
            return 0;
        }
    }

    @Override
    public void addToDeliveryQueue(int orderId) {
        synchronized (lock) {
            while (isOpen() && queueDeliv.size() >= warehouseCapacity) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queueDeliv.add(orderId);
            System.out.println(queueDeliv);
            lock.notifyAll();
        }
    }

    /**
     * Make order.
     */
    public void order() {
        synchronized (lock) {
            int orderId = pizzaCounter++;
            addToQueue(orderId);
            System.out.println(orderId + " ORDER_RECEIVED");
        }
    }

    /**
     * Stop the pizzeria.
     */
    public void stop() {
        close();
        System.out.println("Pizzeria closed.");

        for (Thread cookerThread : cookerThreads) {
            try {
                cookerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Pizzeria stopped.");
        while (!queueDeliv.isEmpty()) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        deliveryFinished.set(true);
        lock.notifyAll();
        System.out.println("Pizzeria closedededed.");
        for (Thread delivererThread : delivererThreads) {
            try {
                delivererThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("All threads have finished.");
    }

    /**
     * Reading JSON.
     *
     * @param configFile - path to JSON
     * @return Pizzeria
     * @throws IOException reading error
     */
    public static Pizzeria fromJson(String configFile) throws IOException {
        List<Integer> cookingTime = new ArrayList<>();
        List<Integer> deliverTime = new ArrayList<>();
        int warehouseCapacity = 0;

        try (InputStream inputStream = Pizzeria.class.getClassLoader()
                .getResourceAsStream(configFile);

            JsonReader reader = Json.createReader(inputStream)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Config file not found: " + configFile);
            }

            JsonObject config = reader.readObject();

            JsonArray cookingTimeArray = config.getJsonArray("cookingTime");
            for (int i = 0; i < cookingTimeArray.size(); i++) {
                cookingTime.add(cookingTimeArray.getInt(i));
            }

            JsonArray deliverTimeArray = config.getJsonArray("deliverTime");
            for (int i = 0; i < deliverTimeArray.size(); i++) {
                deliverTime.add(deliverTimeArray.getInt(i));
            }

            warehouseCapacity = config.getInt("warehouseCapacity", 5);
        }

        return new Pizzeria(cookingTime, deliverTime, warehouseCapacity);
    }
}