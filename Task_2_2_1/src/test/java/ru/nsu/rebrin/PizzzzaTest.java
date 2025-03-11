package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PizzzzaTest {

    @Test
    public void testPizzzza() throws InterruptedException {
        // Подготовка данных
        List<Integer> cookingTimes = List.of(100, 100, 100);
        List<Integer> deliveryTimes = List.of(200, 200, 200);
        Pizzeria pizzeria = new Pizzeria(cookingTimes, deliveryTimes, 5); // Устанавливаем вместимость склада

        // Эмуляция ввода пользователя
        String input = "order" + System.lineSeparator() + "stop" + System.lineSeparator();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(inputStream);

        // Перенаправление вывода в ByteArrayOutputStream для проверки
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        // Запуск метода letsWork в отдельном потоке
        Pizzzza pizzzza = new Pizzzza(pizzeria, scanner);
        Thread thread = new Thread(pizzzza::letsWork);
        thread.start();

        // Даем время для выполнения
        Thread.sleep(10000);

        // Проверка вывода
        String output = outputStream.toString();
        assertTrue(output.contains("Order"), "Test failed: 'Order' message not found");
        assertTrue(output.contains("1 COOKED"), "Test failed: 'Cooked pizza 1' message not found");
        assertTrue(output.contains("1 DELIVERED"), "Test failed: 'Delivered pizza 1' message not found");
        assertTrue(output.contains("Pizzeria closed."), "Test failed: 'Pizzeria closed.' message not found");
        assertTrue(output.contains("All threads have finished."), "Test failed: 'All threads have finished.' message not found");

        // Ожидаем завершения потока
        thread.join();

        // Проверка, что пиццерия закрыта
        assertFalse(pizzeria.isOpen(), "Pizzeria should be closed after stop");

        // Проверка, что все потоки завершены
        for (Thread t : pizzeria.cookerThreads) {
            assertFalse(t.isAlive(), "Cooker thread should be terminated");
        }
        for (Thread t : pizzeria.delivererThreads) {
            assertFalse(t.isAlive(), "Deliverer thread should be terminated");
        }

        System.out.println("Pizzzza tests passed!");
    }
}