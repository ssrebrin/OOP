package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {

    @Test
    void testSnakeInitialPosition() {
        Point start = new Point(3, 3);
        Snake snake = new Snake(start);
        assertEquals(start, snake.points.getFirst());
        assertTrue(snake.alive);
    }

    @Test
    void testMoveRightWithoutApples() {
        Snake snake = new Snake(new Point(2, 2));
        snake.move(10, 10, List.of(), List.of(), List.of());
        assertEquals(new Point(3, 2), snake.points.getFirst());
    }

    @Test
    void testChangeDirectionTowardApple() {
        Snake snake = new Snake(new Point(2, 2));
        List<Point> apples = List.of(new Point(2, 1)); // выше головы

        snake.move(10, 10, apples, List.of(), List.of());

        assertEquals(SnakeModel.Direction.UP, snake.direction);
        assertEquals(new Point(2, 1), snake.points.getFirst());
    }

    @Test
    void testNearReturnsClosestApple() {
        Snake snake = new Snake(new Point(0, 0));
        Point nearest = snake.near(new Point(0, 0), List.of(new Point(5, 5), new Point(1, 0)));
        assertEquals(new Point(1, 0), nearest);
    }

    @Test
    void testDieAndRevive() throws InterruptedException {
        Snake snake = new Snake(new Point(2, 2));
        snake.die();
        assertFalse(snake.alive);
        assertEquals(0, snake.points.size());

        Thread.sleep(3100); // Ждём больше, чем cd

        snake.revive(new Point(1, 1));
        assertTrue(snake.alive);
        assertEquals(new Point(1, 1), snake.points.getFirst());
    }

    @Test
    void testCannotReviveBeforeCooldown() throws InterruptedException {
        Snake snake = new Snake(new Point(2, 2));
        snake.die();
        assertFalse(snake.alive);

        Thread.sleep(1000); // Меньше cd

        snake.revive(new Point(1, 1));
        assertFalse(snake.alive);
    }

    @Test
    void testEatAppleSuccessful() {
        Snake snake = new Snake(new Point(2, 2));
        snake.prevTail = new Point(1, 2); // нужно для роста
        List<Point> apples = new LinkedList<>(List.of(new Point(2, 2)));

        boolean result = snake.eatApple(apples);

        assertTrue(result);
        assertEquals(2, snake.points.size()); // Увеличился
        assertTrue(apples.isEmpty());
    }

    @Test
    void testEatAppleFails() {
        Snake snake = new Snake(new Point(2, 2));
        List<Point> apples = new LinkedList<>(List.of(new Point(3, 3)));

        boolean result = snake.eatApple(apples);

        assertFalse(result);
        assertEquals(1, snake.points.size()); // Не изменился
        assertEquals(1, apples.size());
    }

    @Test
    void testChangeDirBasicCases() {
        Snake snake = new Snake(new Point(2, 2));
        snake.direction = SnakeModel.Direction.RIGHT;
        snake.changeDir(new Point(2, 2), new Point(2, 0)); // вверх
        assertEquals(SnakeModel.Direction.UP, snake.direction);
    }
}
