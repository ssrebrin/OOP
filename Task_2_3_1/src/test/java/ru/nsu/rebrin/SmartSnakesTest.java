package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class SmartSnakesTest {

    @Test
    void testSmartSnakesInitialization() {
        Point start = new Point(2, 2);
        SmartSnakes smartSnakes = new SmartSnakes(List.of(start), 1);
        int i = 0;
        for (Snake a : smartSnakes.getSnake()){
            i++;
        }
        assertEquals(1, i);
        assertTrue(smartSnakes.getSnake().iterator().next() instanceof SmartSnake);
        assertEquals(start, smartSnakes.getSnake().iterator().next().points.getFirst());
    }
}
