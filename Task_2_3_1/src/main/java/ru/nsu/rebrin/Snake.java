package ru.nsu.rebrin;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Snake {

    public SnakeModel.Direction direction = SnakeModel.Direction.RIGHT;
    long deathTime;
    long cd = 3000;
    LinkedList<Point> points;
    boolean alive = true;
    Point prevTail;

    public Snake(Point p) {
        points = new LinkedList<>();
        points.add(p);
    }

    public void move(int height, int width, List<Point> apples, List<Point> danger, List<Point> pot) {
        Point head = points.getFirst();
        Point near = near(head, apples);
        if (near != null) {
            changeDir(head, near);
        }
        Point newHead = switch (direction) {
            case UP -> new Point(head.x, head.y - 1);
            case DOWN -> new Point(head.x, head.y + 1);
            case LEFT -> new Point(head.x - 1, head.y);
            case RIGHT -> new Point(head.x + 1, head.y);
        };
        points.addFirst(newHead);
        prevTail = points.removeLast();
    }

    Point near(Point head, List<Point> apples) {
        if (apples == null || apples.isEmpty()) {
            return null;
        }

        Point nearest = apples.get(0);
        int minDistance = distance(head, nearest);

        for (Point apple : apples) {
            int currentDistance = distance(head, apple);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                nearest = apple;
            }
        }

        return nearest;
    }

    private int distance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y); // Манхэттенское расстояние
    }

    public void revive(Point p) {
        if (!alive && System.currentTimeMillis() - deathTime >= cd) {
            alive = true;
            points = new LinkedList<>();
            points.addFirst(p);
        }
    }

    public void die() {
        if (!alive) {
            return;
        }
        alive = false;
        deathTime = System.currentTimeMillis();
        points = new LinkedList<>();
    }

    void changeDir(Point head, Point near) {
        if (head.x == near.x) {
            if (head.y - near.y > 0) {
                if (direction != SnakeModel.Direction.DOWN) {
                    direction = SnakeModel.Direction.UP;
                    return;
                } else {
                    direction = SnakeModel.Direction.LEFT;
                }
            } else {
                if (direction != SnakeModel.Direction.UP) {
                    direction = SnakeModel.Direction.DOWN;
                    return;
                } else {
                    direction = SnakeModel.Direction.LEFT;
                }

            }
        }
        if (head.y == near.y) {
            if (head.x - near.x > 0) {
                if (direction != SnakeModel.Direction.RIGHT) {
                    direction = SnakeModel.Direction.LEFT;
                    return;
                } else {
                    direction = SnakeModel.Direction.DOWN;
                }
            } else {
                if (direction != SnakeModel.Direction.LEFT) {
                    direction = SnakeModel.Direction.RIGHT;
                    return;
                } else {
                    direction = SnakeModel.Direction.DOWN;
                }
            }
        }
        if (head.x - near.x > 0) {
            if (direction != SnakeModel.Direction.RIGHT) {
                direction = SnakeModel.Direction.LEFT;
                return;
            }
        } else {
            if (direction != SnakeModel.Direction.LEFT) {
                direction = SnakeModel.Direction.RIGHT;
                return;
            }
        }
        if (head.y - near.y > 0) {
            if (direction != SnakeModel.Direction.DOWN) {
                direction = SnakeModel.Direction.UP;
                return;
            }
        } else {
            if (direction != SnakeModel.Direction.UP) {
                direction = SnakeModel.Direction.DOWN;
                return;
            }
        }

    }

    public boolean eatApple(List<Point> apple) {
        Point head = points.getFirst();
        Iterator<Point> iterator = apple.iterator();
        while (iterator.hasNext()) {
            Point point = iterator.next();
            if (head.equals(point)) {
                points.add(prevTail);
                iterator.remove();  // безопасное удаление
                return true;
            }
        }
        return false;
    }

}
