package ru.nsu.rebrin;

import javax.swing.*;
import java.util.*;
import java.util.function.BiConsumer;

public class SmartSnake extends Snake {

    public SmartSnake(Point start) {
        super(start);
    }

    @Override
    public void move(int height, int width, List<Point> apples, List<Point> danger, List<Point> pot) {
        if (points.isEmpty()) return;

        Point head = points.getFirst();
        List<SnakeModel.Direction> possibleMoves = List.of(SnakeModel.Direction.UP, SnakeModel.Direction.DOWN, SnakeModel.Direction.LEFT, SnakeModel.Direction.RIGHT);

        // Сначала определяем куда можно безопасно двигаться
        SnakeModel.Direction bestMove = null;
        int bestDistance = Integer.MAX_VALUE;
        boolean avoidPot = false;

        for (SnakeModel.Direction dir : possibleMoves) {
            if (!canMove(dir, height, width, danger)) continue;
            if (isReverse(dir)) continue; // запрещаем разворот назад

            Point next = nextPoint(head, dir);

            // Проверка на яблоко
            int distanceToNearestApple = distanceToNearest(next, apples);

            boolean nextInPot = containsPoint(pot, next);

            if (bestMove == null ||
                    (!avoidPot && nextInPot) ||  // если можем избежать pot, избегаем
                    (avoidPot == nextInPot && distanceToNearestApple < bestDistance)) {

                bestMove = dir;
                bestDistance = distanceToNearestApple;
                avoidPot = nextInPot;
            }
        }

        // Если вообще никуда нельзя — стоим на месте (можно улучшить)
        if (bestMove == null) {
            bestMove = direction;
        }

        // Двигаемся
        direction = bestMove;
        Point newHead = nextPoint(head, direction);

        points.addFirst(newHead);
        prevTail = points.removeLast();
    }

    private boolean canMove(SnakeModel.Direction dir, int height, int width, List<Point> danger) {
        Point head = points.getFirst();
        Point next = nextPoint(head, dir);

        // Проверка на выход за границу
        if (next.x < 0 || next.x >= width || next.y < 0 || next.y >= height) return false;

        // Проверка на danger
        if (containsPoint(danger, next)) return false;

        // Проверка на саму себя
        if (points.contains(next)) return false;

        return true;
    }

    private boolean isReverse(SnakeModel.Direction newDir) {
        if (points.size() <= 1) return false;

        switch (direction) {
            case UP: return newDir == SnakeModel.Direction.DOWN;
            case DOWN: return newDir == SnakeModel.Direction.UP;
            case LEFT: return newDir == SnakeModel.Direction.RIGHT;
            case RIGHT: return newDir == SnakeModel.Direction.LEFT;
        }
        return false;
    }

    private Point nextPoint(Point p, SnakeModel.Direction dir) {
        switch (dir) {
            case UP: return new Point(p.x, p.y - 1);
            case DOWN: return new Point(p.x, p.y + 1);
            case LEFT: return new Point(p.x - 1, p.y);
            case RIGHT: return new Point(p.x + 1, p.y);
        }
        return p; // shouldn't happen
    }

    private int distanceToNearest(Point from, List<Point> targets) {
        int minDist = Integer.MAX_VALUE;
        for (Point t : targets) {
            int dist = Math.abs(from.x - t.x) + Math.abs(from.y - t.y); // Манхэттенское расстояние
            minDist = Math.min(minDist, dist);
        }
        return minDist == Integer.MAX_VALUE ? 0 : minDist;
    }

    private boolean containsPoint(List<Point> list, Point p) {
        for (Point q : list) {
            if (q.equals(p)) return true;
        }
        return false;
    }

}
