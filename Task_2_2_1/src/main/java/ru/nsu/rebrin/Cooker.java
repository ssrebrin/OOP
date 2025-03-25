package ru.nsu.rebrin;

/**
 * Cooker.
 */
public class Cooker implements Runnable {
    private final int cookingTime;
    private final DeliveryQueue cookQueue;

    /**
     * Init.
     *
     * @param cookingTime - ct
     * @param cookQueue - cq
     */
    public Cooker(int cookingTime, DeliveryQueue cookQueue) {
        this.cookingTime = cookingTime;
        this.cookQueue = cookQueue;
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        while (cookQueue.isOpen()) {
            try {
                int pizza = cookQueue.getFromQueue();
                if (pizza != 0) {
                    Thread.sleep(this.cookingTime);
                    System.out.println(pizza + " COOKED");
                    this.cookQueue.addToDeliveryQueue(pizza);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}