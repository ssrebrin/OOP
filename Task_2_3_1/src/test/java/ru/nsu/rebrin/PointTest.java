package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class PointTest {
    private Point point;
    private Point pPoint;

    @Test
    void getX() {
        point = new Point(0, 0);
        pPoint = new Point(0, 0);
        assertTrue(point.equals(pPoint));
    }
}