package ru.nsu.rebrin;

import java.util.concurrent.atomic.AtomicBoolean;

public interface CookQueue {

    AtomicBoolean open = new AtomicBoolean(true);

    /**
     * Get from q.
     *
     * @return - pizza
     * @throws InterruptedException - nathrow
     */
    int getFromQueue() throws InterruptedException;

    /**
     * Add to q.
     *
     * @param orderId - order
     */
    void addToQueue(int orderId);

    /**
     * Open.
     * @return - true or false
     */
    default boolean isOpen(){
        return open.get();
    }

    /**
     * Close.
     */
    default void close(){
        open.set(false);
    }
}