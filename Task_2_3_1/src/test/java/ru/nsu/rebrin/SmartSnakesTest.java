package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

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
