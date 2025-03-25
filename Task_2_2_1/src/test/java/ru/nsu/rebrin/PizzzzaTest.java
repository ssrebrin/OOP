package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static  org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * PizzzzaTest class.
 */
public class PizzzzaTest {

    @Test
    @Timeout(15) // Увеличили, но тест выполняется быстрее
    public void testPizzzza() throws InterruptedException {
        List<Integer> cookingTimes = List.of(50, 50, 50); // Быстрее
        List<Integer> deliveryTimes = List.of(100, 100, 100); // Быстрее
        Pizzeria pizzeria = new Pizzeria(cookingTimes, deliveryTimes, 5);

        String input = "order" + System.lineSeparator() + "stop" + System.lineSeparator();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        Pizzzza pizzzza = new Pizzzza(pizzeria, scanner);
        Thread thread = new Thread(pizzzza::letsWork);
        thread.start();

        long start = System.currentTimeMillis();
        while (!outputStream.toString().contains("Pizzeria closed.")) {
            if (System.currentTimeMillis() - start > 12000) { // Ждём макс 12 сек
                fail("Pizzeria did not close in time.");
            }
            Thread.sleep(100);
        }

        thread.join(2000); // Ограничиваем join()

        assertFalse(pizzeria.isOpen(), "Pizzeria should be closed after stop");

        for (Thread t : pizzeria.cookerThreads) {
            assertFalse(t.isAlive(), "Cooker thread should be terminated");
        }
        for (Thread t : pizzeria.delivererThreads) {
            assertFalse(t.isAlive(), "Deliverer thread should be terminated");
        }

        System.out.println("Pizzzza tests passed!");
    }
}