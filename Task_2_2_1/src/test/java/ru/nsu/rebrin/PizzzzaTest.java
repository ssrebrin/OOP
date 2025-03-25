package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static  org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/**
 * PizzzzaTest class.
 */
public class PizzzzaTest {

    @Test
    @Timeout(20) // Увеличиваем общий таймаут
    public void testPizzzza() throws InterruptedException {
        // 1. Настройка (в 2 раза быстрее для CI)
        List<Integer> cookingTimes = List.of(30, 30, 30);
        List<Integer> deliveryTimes = List.of(60, 60, 60);
        Pizzeria pizzeria = new Pizzeria(cookingTimes, deliveryTimes, 5);

        // 2. Эмуляция ввода
        String input = "order" + System.lineSeparator() + "stop" + System.lineSeparator();
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // 3. Перехват вывода
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // 4. Запуск
        Pizzzza pizzzza = new Pizzzza(pizzeria, scanner);
        Thread thread = new Thread(pizzzza::letsWork);
        thread.start();

        // 5. Ожидание завершения с гарантированным таймаутом
        long maxWaitTime = 15000; // 15 секунд максимум
        long start = System.currentTimeMillis();

        while (!outputStream.toString().contains("Pizzeria closed.") &&
                !outputStream.toString().contains("All threads have finished.")) {
            if (System.currentTimeMillis() - start > maxWaitTime) {
                thread.interrupt(); // Прерываем, если зависло
                fail("Test timed out. Output: " + outputStream.toString());
            }
            Thread.sleep(200); // Увеличили интервал проверки
        }

        // 6. Гарантированное завершение
        thread.join(3000); // Даём 3 секунды на завершение

        // 7. Проверки
        assertFalse(pizzeria.isOpen());

    }
}