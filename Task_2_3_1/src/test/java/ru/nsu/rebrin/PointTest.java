package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class PointTest {

    @Test
    void testEqualsSameValues() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(2, 3);
        assertEquals(p1, p2);
    }

    @Test
    void testEqualsSameObject() {
        Point p = new Point(5, 6);
        assertEquals(p, p); // should be equal to itself
    }

    @Test
    void testNotEqualsDifferentX() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(3, 2);
        assertNotEquals(p1, p2);
    }

    @Test
    void testNotEqualsDifferentY() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 3);
        assertNotEquals(p1, p2);
    }

    @Test
    void testNotEqualsNull() {
        Point p = new Point(1, 2);
        assertNotEquals(p, null);
    }

    @Test
    void testNotEqualsDifferentClass() {
        Point p = new Point(1, 2);
        String s = "Not a Point";
        assertNotEquals(p, s);
    }

    @Test
    void testHashCodeConsistency() {
        Point p1 = new Point(7, 8);
        Point p2 = new Point(7, 8);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testHashCodeDifferent() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(2, 1);
        assertNotEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testFieldsAreFinal() {
        Point p = new Point(10, 20);
        assertEquals(10, p.xCoord);
        assertEquals(20, p.yCoord);
    }
}
