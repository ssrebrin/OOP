package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class PizzeriaTest {

    private Pizzeria pizzeria;

    @BeforeEach
    void setUp() throws IOException {
        String configFile = "config.json"; // Path to the configuration file
        pizzeria = Pizzeria.fromJson(configFile);
    }

    @Test
    void testPizzeriaCreation() throws IOException {
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
        assertFalse(pizzeria.isDeliveryFinished(), "Delivery should be finished after stop");
    }

    @Test
    void testGetFromQuCInterrupted() throws InterruptedException {
        if (!pizzeria.cookerThreads.isEmpty()) {
            pizzeria.cookerThreads.get(0).interrupt();
        }
        assertEquals(0, pizzeria.queueCook.size(),
                "Queue cook should be empty after interruption");
    }

    @Test
    void testGetFromQuDInterrupted() throws InterruptedException {
        if (!pizzeria.delivererThreads.isEmpty()) {
            pizzeria.delivererThreads.get(0).interrupt();
        }
        assertEquals(0, pizzeria.queueDeliv.size(),
                "Queue deliver should be empty after interruption");
    }
}
