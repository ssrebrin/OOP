package ru.nsu.rebrin;

/**
 * DelivQ.
 */
public interface DeliveryQueue extends CookQueue {
    /**
     * Get from q.
     *
     * @return - pizza
     * @throws InterruptedException - nathrow
     */
    int getFromDeliveryQueue() throws InterruptedException;

    /**
     * Add to q.
     *
     * @param orderId - order
     */
    void addToDeliveryQueue(int orderId);

    /**
     * Is this finished.
     *
     * @return - true or false
     */
    boolean isDeliveryFinished();
}