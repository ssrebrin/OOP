package ru.nsu.rebrin;

import java.util.Objects;

/**
 * Point.
 */
public final class Point {
    final int xCoord;
    final int yCoord;

    /**
     * Init.
     *
     * @param xCoord - xCoord
     * @param yCoord - yCoord
     */
    public Point(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * Equals.
     *
     * @param o - object
     * @return - true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return xCoord == point.xCoord && yCoord == point.yCoord;
    }

    /**
     * Hashcode.
     *
     * @return - hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(xCoord, yCoord);
    }
}
