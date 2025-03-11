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


/**
 * Pizzeria class.
 */
public class Pizzeria {
    private final Object lock = new Object();
    AtomicBoolean open = new AtomicBoolean(true);
    private AtomicBoolean deliveryFinished = new AtomicBoolean(false); // Флаг завершения доставки
    private int pizzaCounter;
    private final int warehouseCapacity; // Максимальная вместимость склада
    List<Integer> queueCook = new ArrayList<>();
    List<Integer> queueDeliv = new ArrayList<>();

    List<Thread> cookerThreads = new ArrayList<>();
    List<Thread> delivererThreads = new ArrayList<>();

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
        for (int i : cookingTime) {
            Thread cookerThread = new Thread(new Cooker(i));
            cookerThreads.add(cookerThread);
            cookerThread.start();
        }

        // Запуск доставщиков
        for (int i : deliverTime) {
            Thread delivererThread = new Thread(new Deliver(i));
            delivererThreads.add(delivererThread);
            delivererThread.start();
        }
    }

    /**
     * Get open status.
     *
     * @return - true or false
     */
    public boolean isOpen() {
        return open.get();
    }

    /**
     * Make order.
     */
    public void order() {
        synchronized (lock) { // Синхронизация на объекте lock
            int orderId = pizzaCounter++;
            queueCook.add(orderId);
            System.out.println(orderId + " ORDER_RECEIVED"); // Вывод состояния заказа
            lock.notifyAll(); // Уведомляем все потоки, синхронизированные на lock
        }
    }

    /**
     * Stop the pizzeria.
     */
    public void stop() {
        synchronized (lock) { // Синхронизация на объекте lock
            open.set(false);
            System.out.println("Pizzeria closed.");
            lock.notifyAll(); // Будим все потоки, синхронизированные на lock
        }

        // Ожидаем завершения всех потоков поваров
        for (Thread cookerThread : cookerThreads) {
            try {
                cookerThread.join(); // Ждём завершения потока
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Ожидаем, пока все заказы будут доставлены
        synchronized (lock) {
            while (!queueDeliv.isEmpty()) {
                try {
                    lock.wait(); // Ждём, пока очередь доставки не станет пустой
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            deliveryFinished.set(true); // Устанавливаем флаг завершения доставки
            lock.notifyAll(); // Будим все потоки доставщиков
        }

        // Ожидаем завершения всех потоков доставщиков
        for (Thread delivererThread : delivererThreads) {
            try {
                delivererThread.join(); // Ждём завершения потока
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("All threads have finished.");
    }

    /**
     * Get from cooker queue.
     *
     * @return - pizza id
     */
    private int getFromQuC() {
        synchronized (lock) { // Синхронизация на объекте lock
            while (queueCook.isEmpty()) {
                if (!open.get()) {
                    return 0; // Завершаем работу, если пиццерия закрыта
                }
                try {
                    lock.wait(); // Ожидаем новых заказов
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    if (!open.get()) {
                        return 0; // Завершаем работу при прерывании, если пиццерия закрыта
                    }
                }
            }
            return queueCook.remove(0);
        }
    }

    /**
     * Set to Delivery queue.
     *
     * @param id - pizza id
     */
    private void setToQuD(int id) {
        synchronized (lock) { // Синхронизация на объекте lock
            while (queueDeliv.size() >= warehouseCapacity) {
                try {
                    lock.wait(); // Ждём, пока место на складе не освободится
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queueDeliv.add(id);
            lock.notifyAll(); // Уведомляем все потоки, синхронизированные на lock
        }
    }

    /**
     * Get from delivery queue.
     *
     * @return - pizza id
     */
    private int getFromQuD() {
        synchronized (lock) { // Синхронизация на объекте lock
            while (queueDeliv.isEmpty()) {
                if (deliveryFinished.get()) {
                    return 0; // Завершаем работу, если доставка завершена
                }
                try {
                    lock.wait(); // Ожидаем новых заказов
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    if (deliveryFinished.get()) {
                        return 0; // Завершаем работу при прерывании, если доставка завершена
                    }
                }
            }
            int pizza = queueDeliv.remove(0);
            lock.notifyAll(); // Уведомляем поваров, что место на складе освободилось
            return pizza;
        }
    }

    /**
     * Cooker class.
     */
    private class Cooker implements Runnable {
        private final int cookingTime;

        private Cooker(int cookingTime) {
            this.cookingTime = cookingTime;
        }

        @Override
        public void run() {
            while (open.get()) {
                int pizza = getFromQuC();
                if (pizza != 0) {
                    try {
                        Thread.sleep(this.cookingTime);
                        setToQuD(pizza);
                        System.out.println(pizza + " COOKED"); // Вывод состояния заказа
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    /**
     * Deliver class.
     */
    private class Deliver implements Runnable {
        private final int deliveringTime;

        private Deliver(int deliveringTime) {
            this.deliveringTime = deliveringTime;
        }

        @Override
        public void run() {
            while (open.get() || !deliveryFinished.get()) {
                int pizza = getFromQuD();
                if (pizza != 0) {
                    try {
                        Thread.sleep(deliveringTime);
                        System.out.println(pizza + " DELIVERED"); // Вывод состояния заказа
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
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

        // Используем ClassLoader для чтения файла из ресурсов
        try (InputStream inputStream =
                 Pizzeria.class.getClassLoader().getResourceAsStream(configFile);
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

            warehouseCapacity = config.getInt("warehouseCapacity", 5); // Значение по умолчанию 5
        }

        return new Pizzeria(cookingTime, deliverTime, warehouseCapacity);
    }


}