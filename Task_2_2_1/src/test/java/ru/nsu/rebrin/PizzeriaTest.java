package ru.nsu.rebrin;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PizzeriaTest {

    private Pizzeria pizzeria;
    private List<Integer> cookingTimes;
    private List<Integer> deliverTimes;

    @BeforeEach
    void setUp() throws IOException {
        String configFile = "config.json"; // Путь к вашему JSON-файлу конфигурации
        pizzeria = Pizzeria.fromJson(configFile);
    }

    @Test
    void testPizzeriaCreation() throws IOException {
        String configFile = "config.json";
        Pizzeria pizzeria = Pizzeria.fromJson(configFile);
        assertNotNull(pizzeria, "Pizzeria should be created");
        assertTrue(pizzeria.isOpen(), "Pizzeria should be open after creation");
    }

    @Test
    void testPizzeriaCreationWithInvalidCookingTime() {
        String invalidConfigFile = "invalid_cooking_time_config.json";
        assertThrows(IllegalArgumentException.class, () -> {
            Pizzeria.fromJson(invalidConfigFile);
        }, "Should throw IllegalArgumentException for invalid cooking times");
    }

    @Test
    void testPizzeriaCreationWithInvalidDeliverTime() {
        String invalidConfigFile = "invalid_deliver_time_config.json";
        assertThrows(IllegalArgumentException.class, () -> {
            Pizzeria.fromJson(invalidConfigFile);
        }, "Should throw IllegalArgumentException for invalid deliver times");
    }


    @Test
    void testStop() throws InterruptedException, IOException {
        String configFile = "config.json";
        Pizzeria pizzeria = Pizzeria.fromJson(configFile);
        pizzeria.order();
        pizzeria.order();

        Thread stopThread = new Thread(() -> {
            try {
                Thread.sleep(500);
                pizzeria.stop();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        stopThread.start();

        stopThread.join();
        assertFalse(pizzeria.isOpen(), "Pizzeria should be closed after stop");
    }

    @Test
    void testCooker() throws InterruptedException {
        pizzeria.order();
        Thread.sleep(500);
        assertEquals(0, pizzeria.queueCook.size(), "Queue should be empty after cooking");
        assertEquals(0, pizzeria.queueDeliv.size(), "Queue should be empty after cooking");
    }


    @Test
    void testDeliveryFinished() throws InterruptedException {
        pizzeria.order();
        Thread.sleep(500);
        pizzeria.stop();
        assertFalse(pizzeria.open.get(), "Delivery should be finished after stop");
    }

    @Test
    void testGetFromQuCoInterrupted() throws InterruptedException {
        if (!pizzeria.cookerThreads.isEmpty()) {
            pizzeria.cookerThreads.get(0).interrupt();
        }

        assertEquals(0, pizzeria.queueCook.size(),
            "Queue cook should be empty after interruption");
    }

    @Test
    void testGetFromQuDeInterrupted() throws InterruptedException {
        if (!pizzeria.delivererThreads.isEmpty()) {
            pizzeria.delivererThreads.get(0).interrupt();
        }

        assertEquals(0, pizzeria.queueDeliv.size(),
            "Queue deliver should be empty after interruption");
    }
}