package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SnakesTest {

    Point p1 = new Point(1, 1);
    Point p2 = new Point(2, 2);
    Point p3 = new Point(3, 3);
    Snakes snakes;

    @BeforeEach
    void setup() {
        snakes = new Snakes(List.of(p1, p2), 1);
    }

    @Test
    void testInitialization() {
        assertEquals(2, snakes.getSize());
        assertEquals(p1, snakes.getSnake().iterator().next().points.getFirst());
    }

    @Test
    void testSelfCollisionKillsSnake() {
        Snake s = new Snake(new Point(0, 0), 1);
        s.points.add(new Point(0, 1));
        s.points.add(new Point(0, 0)); // head collides with body
        snakes = new Snakes();
        snakes.addSnakes(s);

        snakes.checkSelf();

        assertFalse(s.alive);
    }

    @Test
    void testCollisionWithOtherSnakeHead() {
        Snake s1 = new Snake(new Point(5, 5), 1);
        Snake s2 = new Snake(new Point(5, 5), 2); // same head

        snakes = new Snakes();
        snakes.addSnakes(s1);
        snakes.addSnakes(s2);

        snakes.checkSelves();

        assertFalse(s1.alive);
        assertFalse(s2.alive);
    }

    @Test
    void testCollisionWithOtherSnakeBody() {
        Snake s1 = new Snake(new Point(5, 5), 1);
        s1.points.add(new Point(4, 5));
        s1.points.add(new Point(3, 5));

        Snake s2 = new Snake(new Point(4, 5), 2); // head hits body of s1

        snakes = new Snakes();
        snakes.addSnakes(s1);
        snakes.addSnakes(s2);

        snakes.checkSelves();

        assertTrue(s1.alive);
        assertFalse(s2.alive);
    }

    @Test
    void testCheckOthersKillsCollidingSnakes() {
        Snake s1 = new Snake(new Point(7, 7), 1);
        s1.points.add(new Point(6, 7));

        Snake s2 = new Snake(new Point(6, 7), 2);

        Snakes others = new Snakes();
        others.addSnakes(s2);

        snakes = new Snakes();
        snakes.addSnakes(s1);

        others.checkOthers(snakes);

        assertFalse(s2.alive);
        assertTrue(s1.alive);
    }

    @Test
    void testCheckListKillsSnake() {
        Snake s = new Snake(new Point(1, 2), 1);
        snakes = new Snakes();
        snakes.addSnakes(s);

        List<Point> enemyBody = List.of(new Point(9, 9), new Point(1, 2)); // matches head

        snakes.checkList(enemyBody);

        assertFalse(s.alive);
    }

    @Test
    void testCollisOnHead() {
        Point hit = snakes.getSnake().iterator().next().points.getFirst();
        boolean result = snakes.collis(hit);
        assertTrue(result);
        assertFalse(snakes.getSnake().iterator().next().alive);
    }

    @Test
    void testCollisOnBodyOnly() {
        Snake s = new Snake(new Point(5, 5), 1);
        s.points.add(new Point(5, 6));
        s.points.add(new Point(5, 7));
        snakes.addSnakes(s);

        boolean result = snakes.collis(new Point(5, 7));
        assertTrue(result);
        assertTrue(s.alive); // alive because only head collisions kill
    }

    @Test
    void testCheckAppleEaten() {
        Snake s = new Snake(new Point(4, 4), 1);
        s.prevTail = new Point(3, 4); // needed to grow

        snakes = new Snakes();
        snakes.addSnakes(s);

        List<Point> apples = new LinkedList<>(List.of(new Point(4, 4), new Point(1, 1)));

        int result = snakes.checkApple(apples);

        assertEquals(1, result);
        assertEquals(1, apples.size()); // one apple removed
    }
}