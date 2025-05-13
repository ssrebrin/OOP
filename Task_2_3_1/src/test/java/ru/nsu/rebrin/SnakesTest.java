package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SnakesTest {

    @Test
    void testCollisionWithHeadKillsSnake() {
        Point p1 = new Point(1, 1);
        Snakes snakes = new Snakes(List.of(p1));
        boolean result = snakes.collis(p1);
        assertTrue(result);
        assertFalse(snakes.snakes.get(0).alive);
    }

    @Test
    void testCollisionWithBodyReturnsTrue() {
        Snake snake = new Snake(new Point(0, 0));
        snake.points.add(new Point(0, 1));
        snake.points.add(new Point(0, 2));
        Snakes snakes = new Snakes();
        snakes.snakes.add(snake);

        assertTrue(snakes.collis(new Point(0, 2)));
        assertTrue(snake.alive);
    }

    @Test
    void testCheckSelfKillsSnakeOnSelfCollision() {
        Snake snake = new Snake(new Point(0, 0));
        snake.points.add(new Point(0, 1));
        snake.points.add(new Point(0, 0)); // loops back

        Snakes snakes = new Snakes();
        snakes.snakes.add(snake);
        snakes.checkSelf();

        assertFalse(snake.alive);
    }

    @Test
    void testCheckSelvesKillsOnHeadToHead() {
        Snake s1 = new Snake(new Point(0, 0));
        Snake s2 = new Snake(new Point(0, 0));
        Snakes snakes = new Snakes();
        snakes.snakes.addAll(List.of(s1, s2));

        snakes.checkSelves();

        assertFalse(s1.alive);
        assertFalse(s2.alive);
    }

    @Test
    void testCheckOthersKillsOnCollision() {
        Snake s1 = new Snake(new Point(0, 0));
        Snake s2 = new Snake(new Point(0, 0));

        Snakes main = new Snakes();
        Snakes others = new Snakes();
        main.snakes.add(s1);
        others.snakes.add(s2);

        main.checkOthers(others);

        assertFalse(s1.alive);
        assertFalse(s2.alive);
    }

    @Test
    void testCheckListKillsOnBodyHit() {
        Snake s = new Snake(new Point(0, 0));
        s.points.add(new Point(1, 1));
        s.points.add(new Point(2, 2));

        Snakes snakes = new Snakes();
        snakes.snakes.add(s);
        snakes.checkList(List.of(new Point(9, 9), new Point(2, 2)));

        assertFalse(s.alive);
    }

    @Test
    void testCheckAppleIncrementsCounter() {
        Snake s = new Snake(new Point(0, 0));
        s.points.add(new Point(0, 1));

        List<Point> apples = List.of(new Point(0, 1));

        Snakes snakes = new Snakes();
        snakes.snakes.add(s);
        int eaten = snakes.checkApple(apples);

        assertEquals(1, eaten);
    }
}
