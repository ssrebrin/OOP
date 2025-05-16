package ru.nsu.rebrin;

import java.util.Objects;

public final class Point {
    final int x;
    final int y;

    /**
     * Init.
     *
     * @param x - x
     * @param y - y
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Equals.
     *
     * @param o - object
     * @return - true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    /**
     * Hashcode.
     *
     * @return - hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
