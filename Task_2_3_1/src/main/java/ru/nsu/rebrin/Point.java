package ru.nsu.rebrin;

import java.util.Objects;

public final class Point {
    final int x;
    final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean isCollision(int height, int width) {
        System.out.println(height);
        System.out.println(width);
        return x < 0 || x >= width || y < 0 || y >= height;
    }
}
