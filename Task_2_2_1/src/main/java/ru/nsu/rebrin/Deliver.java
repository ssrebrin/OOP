package ru.nsu.rebrin;

/**
 * Deliver class.
 */
public class Deliver implements Runnable {
    private final int deliveringTime;
    private final Pizzeria pizzeria;

    /**
     * Init.
     *
     * @param deliveringTime - deliveringTime
     * @param pizzeria - pizzeria
     */
    public Deliver(int deliveringTime, Pizzeria pizzeria) {
        this.deliveringTime = deliveringTime;
        this.pizzeria = pizzeria;
    }

    /**
     * Run.
     */
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