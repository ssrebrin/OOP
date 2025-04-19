package ru.nsu.rebrin;

public final class Point {
    final int x;
    final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Point o) {
        return this.x == o.x && this.y == o.y;
    }
}
