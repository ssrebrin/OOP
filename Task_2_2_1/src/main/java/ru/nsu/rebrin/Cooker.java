package ru.nsu.rebrin;

/**
 * Cooker.
 */
public class Cooker implements Runnable {
    private final int cookingTime;
    private final Pizzeria pizzeria;

    /**
     * Init.
     *
     * @param cookingTime - cookingTime
     * @param pizzeria - pizzeria
     */
    public Cooker(int cookingTime, Pizzeria pizzeria) {
        this.cookingTime = cookingTime;
        this.pizzeria = pizzeria;
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        while (pizzeria.isOpen()) {
            int pizza = pizzeria.getFromQuC();
            if (pizza != 0) {
                try {
                    Thread.sleep(this.cookingTime);
                    pizzeria.setToQuD(pizza);
                    System.out.println(pizza + " COOKED"); // Вывод состояния заказа
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}