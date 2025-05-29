package ru.nsu.rebrin;

import java.util.Objects;

/**
 * Point.
 */
public final class Point {
    final int xxCoord;
    final int yyCoord; 

    /**
     * Init.
     *
     * @param xxCoord - xxCoord
     * @param yyCoord - yyCoord
     */
    public Point(int xxCoord, int yyCoord) {
        this.xxCoord = xxCoord;
        this.yyCoord = yyCoord;
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
        return xxCoord == point.xxCoord && yyCoord == point.yyCoord;
    }

    /**
     * Hashcode.
     *
     * @return - hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(xxCoord, yyCoord);
    }
}