package ru.nsu.rebrin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Pizzeria {
    private AtomicBoolean open = new AtomicBoolean(true);
    private int deliverers;
    private int cookers;
    private int warehouse_cup;
    private int pizza_counter;
    private List<Integer> queue_cook = new ArrayList<>();
    private List<Integer> queue_deliv = new ArrayList<>();

    private List<Thread> cookerThreads = new ArrayList<>();
    private List<Thread> delivererThreads = new ArrayList<>();

    public Pizzeria(int deliverers, int cookers, int warehouse_cup, List<Integer> cooking_time, List<Integer> deliver_time) {
        this.deliverers = deliverers;
        this.cookers = cookers;
        this.warehouse_cup = warehouse_cup;
        this.pizza_counter = 1;

        // Запуск поваров
        for (int i : cooking_time) {
            Thread cookerThread = new Thread(new Cooker(i));
            cookerThreads.add(cookerThread);
            cookerThread.start();
        }

        // Запуск доставщиков
        for (int i : deliver_time) {
            Thread delivererThread = new Thread(new Deliver(i));
            delivererThreads.add(delivererThread);
            delivererThread.start();
        }
    }

    public static void main(String[] args) {
        List<Integer> t = List.of(3000, 3000, 3000, 3000, 3000);
        List<Integer> tt = List.of(5000, 5000, 5000, 5000, 5000);

        Pizzeria pizza = new Pizzeria(3, 3, 5, t, tt);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write");

        while (pizza.open.get()) {
            String userInput = scanner.nextLine();
            if ("order".equals(userInput)) {
                System.out.println("Order");
                pizza.order();
            } else if ("stop".equals(userInput)) {
                pizza.stop();
                break; // Выход из цикла после остановки
            } else if ("order2".equals(userInput)) {
                System.out.println("Order");
                pizza.order();
                pizza.order();
            } else if ("order6".equals(userInput)) {
                System.out.println("Order");
                pizza.order();
                pizza.order();
                pizza.order();
                pizza.order();
                pizza.order();
                pizza.order();
            }
        }

        // Закрываем Scanner
        scanner.close();
    }

    public synchronized void order() {
        queue_cook.add(pizza_counter++);
        System.out.println("Order received. Queue size: " + queue_cook.size());
        notifyAll();
    }

    public synchronized void stop() {
        open.set(false);
        System.out.println("Pizzeria closed.");
        notifyAll(); // Будим все потоки, чтобы они могли завершиться

        // Ожидаем завершения всех потоков поваров
        for (Thread cookerThread : cookerThreads) {
            try {
                cookerThread.join(); // Ждём завершения потока
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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

    private synchronized int get_from_q_c() {
        while (queue_cook.isEmpty()) {
            if (!open.get()) return 0;
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return queue_cook.remove(0);
    }

    private synchronized void add_to_q_d(int id) {
        queue_deliv.add(id);
        notifyAll();
    }

    private synchronized int get_from_q_d() {
        while (queue_deliv.isEmpty()) {
            if (!open.get()) return 0;
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return queue_deliv.remove(0);
    }

    private class Cooker implements Runnable {
        private final int cooking_time;

        private Cooker(int cooking_time) {
            this.cooking_time = cooking_time;
        }

        @Override
        public void run() {
            while (open.get()) {
                int pizza = get_from_q_c();
                if (pizza != 0) {
                    try {
                        Thread.sleep(this.cooking_time);
                        System.out.println("Cooked pizza " + pizza);
                        add_to_q_d(pizza);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }

    private class Deliver implements Runnable {
        private final int delivering_time;

        private Deliver(int delivering_time) {
            this.delivering_time = delivering_time;
        }

        @Override
        public void run() {
            while (open.get()) {
                int pizza = get_from_q_d();
                if (pizza != 0) {
                    try {
                        Thread.sleep(delivering_time);
                        System.out.println("Delivered pizza " + pizza);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }
}