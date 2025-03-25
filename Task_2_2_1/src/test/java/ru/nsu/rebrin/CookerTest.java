package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CookerTest {

    private Pizzeria pizzeria;

    @BeforeEach
    void setUp() throws IOException {
        String configFile = "config.json"; // Path to the configuration file
        pizzeria = Pizzeria.fromJson(configFile);
    }

    @Test
    void testCookerRunsCorrectly() throws InterruptedException {
        pizzeria.order();
        Thread.sleep(4000);
        assertEquals(0, pizzeria.queueCook.size(), "Queue should be empty after cooking");
        assertTrue(pizzeria.queueDeliv.size() == 0, "Delivery queue should be empty cooked pizzas");
    }
}
