package ru.nsu.rebrin;

public class Deliver implements Runnable {
    private final int deliveringTime;
    private final Pizzeria pizzeria;

    public Deliver(int deliveringTime, Pizzeria pizzeria) {
        this.deliveringTime = deliveringTime;
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        while (pizzeria.isOpen() || !pizzeria.isDeliveryFinished()) {
            int pizza = pizzeria.getFromQuD();
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