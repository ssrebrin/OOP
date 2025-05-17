package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

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
        snake.move(10, 10, List.of(), List.of());
        assertEquals(new Point(3, 2), snake.points.getFirst());
    }

    @Test
    void testChangeDirectionTowardApple() {
        Snake snake = new Snake(new Point(2, 2));
        List<Point> apples = List.of(new Point(2, 1)); // выше головы

        snake.move(10, 10, apples, List.of());

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

    @Test
    void testChangeDirPrefersVerticalWhenEqualDistance() {
        Snake snake = new Snake(new Point(2, 2));
        snake.direction = SnakeModel.Direction.RIGHT;

        Point head = new Point(2, 2);
        Point target = new Point(2, 4); // ниже головы

        snake.changeDir(head, target);
        assertEquals(SnakeModel.Direction.DOWN, snake.direction);
    }

    @Test
    void testChangeDirAvoidsReverse() {
        Snake snake = new Snake(new Point(2, 2));
        snake.direction = SnakeModel.Direction.UP;

        Point head = new Point(2, 2);
        Point target = new Point(2, 4); // вниз

        snake.changeDir(head, target);
        assertNotEquals(SnakeModel.Direction.DOWN, snake.direction);
    }

    @Test
    void testChangeDirAvoidsReverseDown() {
        Snake snake = new Snake(new Point(2, 2));
        snake.direction = SnakeModel.Direction.DOWN;

        Point head = new Point(2, 2);
        Point target = new Point(2, 1); // вниз

        snake.changeDir(head, target);
        assertNotEquals(SnakeModel.Direction.UP, snake.direction);
    }

    @Test
    void testChangeDirAvoidsReverseLeftToRight() {
        Snake snake = new Snake(new Point(5, 5));
        snake.direction = SnakeModel.Direction.LEFT;

        Point target = new Point(6, 5);

        snake.changeDir(new Point(5, 5), target);
        // нельзя поворачивать направо (реверс), fallback будет вниз
        assertNotEquals(SnakeModel.Direction.RIGHT, snake.direction);
    }

    @Test
    void testChangeDirAvoidsReverseRightToLeft() {
        Snake snake = new Snake(new Point(5, 5));
        snake.direction = SnakeModel.Direction.RIGHT;

        Point target = new Point(4, 5);

        snake.changeDir(new Point(5, 5), target);
        // нельзя поворачивать направо (реверс), fallback будет вниз
        assertNotEquals(SnakeModel.Direction.LEFT, snake.direction);
    }

    @Test
    void testNearChoosesFirstIfDistancesEqual() {
        Snake snake = new Snake(new Point(0, 0));
        Point apple1 = new Point(1, 0);
        Point apple2 = new Point(0, 1);

        Point nearest = snake.near(new Point(0, 0), List.of(apple1, apple2));
        // оба на расстоянии 1, должен вернуть первый
        assertEquals(apple1, nearest);
    }

    @Test
    void testMoveDoesNotGrowIfNoAppleEaten() {
        Snake snake = new Snake(new Point(4, 4));
        snake.move(10, 10, List.of(), List.of());

        // Длина не изменилась
        assertEquals(1, snake.points.size());
    }

    @Test
    void testMoveUpdatesPrevTailCorrectly() {
        Snake snake = new Snake(new Point(4, 4));
        snake.move(10, 10, List.of(), List.of());

        assertEquals(new Point(4, 4), snake.prevTail);
    }

    @Test
    void testEatMultipleApplesOneEaten() {
        Snake snake = new Snake(new Point(5, 5));
        snake.prevTail = new Point(4, 5);
        List<Point> apples = new LinkedList<>(List.of(new Point(3, 3),
            new Point(5, 5), new Point(6, 6)));

        boolean eaten = snake.eatApple(apples);
        assertTrue(eaten);
        assertEquals(2, snake.points.size());
        assertFalse(apples.contains(new Point(5, 5)));
    }

    @Test
    void testReviveDoesNotClearDeathTimeIfTooSoon() throws InterruptedException {
        Snake snake = new Snake(new Point(5, 5));
        snake.die();

        long beforeRevive = snake.deathTime;
        Thread.sleep(1000); // меньше cd
        snake.revive(new Point(1, 1));
        assertEquals(beforeRevive, snake.deathTime);
    }

    @Test
    void testDirectionUnchangedIfAppleNullOrEmpty() {
        Snake snake = new Snake(new Point(2, 2));
        snake.direction = SnakeModel.Direction.UP;

        snake.move(10, 10, null, List.of());
        assertEquals(SnakeModel.Direction.UP, snake.direction);

        snake.move(10, 10, List.of(), List.of());
        assertEquals(SnakeModel.Direction.UP, snake.direction);
    }

    @Test
    void verticalUpAllowed() {
        Snake snake = new Snake(new Point(5, 5));
        snake.direction = SnakeModel.Direction.RIGHT;
        snake.changeDir(new Point(5, 5), new Point(5, 4));
        assertEquals(SnakeModel.Direction.UP, snake.direction);
    }

    @Test
    void verticalUpBlockedGoesLeft() {
        Snake snake = new Snake(new Point(5, 5));
        snake.direction = SnakeModel.Direction.DOWN;
        snake.changeDir(new Point(5, 5), new Point(5, 4));
        assertEquals(SnakeModel.Direction.LEFT, snake.direction);
    }

    @Test
    void verticalDownAllowed() {
        Snake snake = new Snake(new Point(5, 5));
        snake.direction = SnakeModel.Direction.RIGHT;
        snake.changeDir(new Point(5, 4), new Point(5, 5));
        assertEquals(SnakeModel.Direction.DOWN, snake.direction);
    }

    @Test
    void verticalDownBlockedGoesLeft() {
        Snake snake = new Snake(new Point(5, 4));
        snake.direction = SnakeModel.Direction.UP;
        snake.changeDir(new Point(5, 4), new Point(5, 5));
        assertEquals(SnakeModel.Direction.LEFT, snake.direction);
    }

    @Test
    void horizontalLeftAllowed() {
        Snake snake = new Snake(new Point(5, 5));
        snake.direction = SnakeModel.Direction.UP;
        snake.changeDir(new Point(5, 5), new Point(4, 5));
        assertEquals(SnakeModel.Direction.LEFT, snake.direction);
    }

    @Test
    void horizontalLeftBlockedGoesDown() {
        Snake snake = new Snake(new Point(5, 5));
        snake.direction = SnakeModel.Direction.RIGHT;
        snake.changeDir(new Point(5, 5), new Point(4, 5));
        assertEquals(SnakeModel.Direction.DOWN, snake.direction);
    }

    @Test
    void horizontalRightAllowed() {
        Snake snake = new Snake(new Point(4, 5));
        snake.direction = SnakeModel.Direction.UP;
        snake.changeDir(new Point(4, 5), new Point(5, 5));
        assertEquals(SnakeModel.Direction.RIGHT, snake.direction);
    }

    @Test
    void horizontalRightBlockedGoesDown() {
        Snake snake = new Snake(new Point(4, 5));
        snake.direction = SnakeModel.Direction.LEFT;
        snake.changeDir(new Point(4, 5), new Point(5, 5));
        assertEquals(SnakeModel.Direction.DOWN, snake.direction);
    }

    @Test
    void diagonalLeftPreference() {
        Snake snake = new Snake(new Point(5, 5));
        snake.direction = SnakeModel.Direction.RIGHT;
        snake.changeDir(new Point(5, 5), new Point(4, 4));
        assertEquals(SnakeModel.Direction.UP, snake.direction);
    }

    @Test
    void diagonalRightPreference() {
        Snake snake = new Snake(new Point(4, 4));
        snake.direction = SnakeModel.Direction.LEFT;
        snake.changeDir(new Point(4, 4), new Point(5, 5));
        assertEquals(SnakeModel.Direction.DOWN, snake.direction);
    }

    @Test
    void diagonalUpPreference() {
        Snake snake = new Snake(new Point(4, 4));
        snake.direction = SnakeModel.Direction.DOWN;
        snake.changeDir(new Point(4, 4), new Point(5, 3));
        assertEquals(SnakeModel.Direction.RIGHT, snake.direction);
    }

    @Test
    void diagonalDownPreference() {
        Snake snake = new Snake(new Point(5, 3));
        snake.direction = SnakeModel.Direction.UP;
        snake.changeDir(new Point(5, 3), new Point(4, 4));
        assertEquals(SnakeModel.Direction.LEFT, snake.direction);
    }

}
