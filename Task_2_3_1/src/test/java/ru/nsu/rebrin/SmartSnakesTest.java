package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmartSnakesTest {

    @Test
    void testSmartSnakesInitialization() {
        Point start = new Point(2, 2);
        SmartSnakes smartSnakes = new SmartSnakes(List.of(start));

        assertEquals(1, smartSnakes.snakes.size());
        assertTrue(smartSnakes.snakes.get(0) instanceof SmartSnake);
        assertEquals(start, smartSnakes.snakes.get(0).points.getFirst());
    }
}
