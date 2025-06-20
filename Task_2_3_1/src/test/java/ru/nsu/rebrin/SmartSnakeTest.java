package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class SmartSnakeTest {

    @Test
    void testMoveTowardsApple() {
        SmartSnake snake = new SmartSnake(new Point(2, 2), 1);
        snake.direction = SnakeModel.Direction.UP;

        List<Point> apples = List.of(new Point(2, 1));
        snake.move(10, 10, apples, List.of());

        assertEquals(new Point(2, 1), snake.points.getFirst());
    }

    @Test
    void testAvoidDanger() {
        SmartSnake snake = new SmartSnake(new Point(2, 2), 1);
        snake.direction = SnakeModel.Direction.UP;

        List<Point> apples = List.of(new Point(2, 1));
        List<Point> danger = List.of(new Point(2, 1)); // apple is dangerous!

        snake.move(10, 10, apples, danger);

        assertNotEquals(new Point(2, 1), snake.points.getFirst());
    }

    @Test
    void testNoAvailableMoveKeepsDirection() {
        SmartSnake snake = new SmartSnake(new Point(0, 0), 1);
        snake.direction = SnakeModel.Direction.UP;

        List<Point> danger = List.of(
                new Point(0, 1), new Point(1, 0)
        );

        snake.move(1, 1, List.of(), danger);

        // Since it's blocked everywhere, stays in place
        assertTrue(snake.points.getFirst().equals(new Point(0, -1)));
    }
}