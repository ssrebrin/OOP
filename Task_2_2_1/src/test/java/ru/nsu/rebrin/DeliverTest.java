package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class DeliverTest {

    private Pizzeria pizzeria;

    @BeforeEach
    void setUp() throws IOException {
        String configFile = "config.json"; // Path to the configuration file
        pizzeria = Pizzeria.fromJson(configFile);
    }

    @Test
    @Timeout(10)
    void testDeliveryRunsCorrectly() throws InterruptedException {
        pizzeria.order();
        Thread.sleep(500);
        assertEquals(0, pizzeria.queueCook.size(), "Queue should be empty after cooking");
        assertEquals(0, pizzeria.queueDeliv.size(), "Queue should be empty after delivery");
    }
}
