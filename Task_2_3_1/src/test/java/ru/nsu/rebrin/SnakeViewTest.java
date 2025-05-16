package ru.nsu.rebrin;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//@Disabled("JavaFX tests don't run properly in headless environments")
public class SnakeViewTest {

    @Test
    void testGetSnakeRectangles() {
        LinkedList<Point> snake = new LinkedList<>();
        snake.add(new Point(1, 1));
        snake.add(new Point(2, 2));
        SnakeView view = new SnakeView(10, 10);
        List<Rectangle> rects = view.getSnakeRectangles(snake);

        assertEquals(2, rects.size());
        assertEquals(19, rects.get(0).getWidth());
        assertEquals(20, rects.get(1).getX());
    }

}