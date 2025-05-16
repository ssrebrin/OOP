package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


//@Disabled("JavaFX tests don't run properly in headless environments")
public class SnakeViewTest {

    @Test
    void testGetSnakeRectangles() {
        LinkedList<Point> snake = new LinkedList<>();
        snake.add(new Point(1, 1));
        snake.add(new Point(2, 2));
        List<Rectangle> rects = SnakeView.getSnakeRectangles(snake);

        assertEquals(2, rects.size());
        assertEquals(19, rects.get(0).getWidth());
        assertEquals(40, rects.get(1).getX()); // 2*20
    }
    @Test
    void testCountVowelsInDirections() {
        Map<SnakeModel.Direction, Integer> result = SnakeView.countVowelsInDirections();

        assertEquals(1, result.get(SnakeModel.Direction.UP));    // U
        assertEquals(1, result.get(SnakeModel.Direction.DOWN));  // O
        assertEquals(1, result.get(SnakeModel.Direction.LEFT));  // E
        assertEquals(1, result.get(SnakeModel.Direction.RIGHT)); // I
    }

}