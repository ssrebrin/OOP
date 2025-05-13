package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmartSnakeTest {

    @Test
    void testMoveTowardsApple() {
        SmartSnake snake = new SmartSnake(new Point(2, 2));
        snake.direction = SnakeModel.Direction.UP;

        List<Point> apples = List.of(new Point(2, 1));
        snake.move(10, 10, apples, List.of(), List.of());

        assertEquals(new Point(2, 1), snake.points.getFirst());
    }

    @Test
    void testAvoidDanger() {
        SmartSnake snake = new SmartSnake(new Point(2, 2));
        snake.direction = SnakeModel.Direction.UP;

        List<Point> apples = List.of(new Point(2, 1));
        List<Point> danger = List.of(new Point(2, 1)); // apple is dangerous!

        snake.move(10, 10, apples, danger, List.of());

        assertNotEquals(new Point(2, 1), snake.points.getFirst());
    }

    @Test
    void testNoAvailableMoveKeepsDirection() {
        SmartSnake snake = new SmartSnake(new Point(0, 0));
        snake.direction = SnakeModel.Direction.UP;

        List<Point> danger = List.of(
                new Point(0, 1), new Point(1, 0)
        );

        snake.move(1, 1, List.of(), danger, List.of());

        // Since it's blocked everywhere, stays in place
        assertTrue(snake.points.getFirst().equals(new Point(0, -1)));
    }
}
