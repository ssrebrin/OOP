package ru.nsu.rebrin;

/**
 * Deliver.
 */
public class Deliver implements Runnable {
    private final int deliveringTime;
    private final DeliveryQueue deliveryQueue;

    /**
     * Init.
     *
     * @param deliveringTime - dt
     * @param deliveryQueue - dq
     */
    public Deliver(int deliveringTime, DeliveryQueue deliveryQueue) {
        this.deliveringTime = deliveringTime;
        this.deliveryQueue = deliveryQueue;
    }

    @Override
    public void run() {
        while (deliveryQueue.isOpen() || !deliveryQueue.isDeliveryFinished()) {
            try {
                int pizza = deliveryQueue.getFromDeliveryQueue();
                if (pizza != 0) {
                    Thread.sleep(deliveringTime);
                    System.out.println(pizza + " DELIVERED");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}