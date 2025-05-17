package ru.nsu.rebrin;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a snake in the game, handling movement, growth, and life states.
 * Manages direction changes, apple consumption, death, and revival mechanics.
 */
public class Snake {

    public SnakeModel.Direction direction = SnakeModel.Direction.RIGHT;
    long deathTime;
    long cd = 3000;
    LinkedList<Point> points;
    boolean alive = true;
    Point prevTail;

    /**
     * Constructs a snake with initial head position.
     *
     * @param p Starting point for the snake's head
     */
    public Snake(Point p) {
        points = new LinkedList<>();
        points.add(p);
    }


    /**
     * Moves the snake one cell in current direction.
     *
     * @param height Grid height (for boundary checks)
     * @param width Grid width (for boundary checks)
     * @param apples List of available apples
     * @param danger List of dangerous obstacles
     */
    public void move(int height, int width, List<Point> apples, List<Point> danger) {
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

    /**
     * Finds the nearest apple to snake's head.
     *
     * @return Nearest apple point or null if none exist
     */
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

    /**
     * Calculates Manhattan distance between two points.
     */
    private int distance(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y); // Манхэттенское расстояние
    }

    /**
     * Revives the snake after cooldown period.
     *
     * @param p Revival position
     */
    public void revive(Point p) {
        if (!alive && System.currentTimeMillis() - deathTime >= cd) {
            alive = true;
            points = new LinkedList<>();
            points.addFirst(p);
        }
    }

    /**
     * Kills the snake and starts revival timer.
     */
    public void die() {
        if (!alive) {
            return;
        }
        alive = false;
        deathTime = System.currentTimeMillis();
        points = new LinkedList<>();
    }

    /**
     * Adjusts direction toward nearest apple.
     */
    void changeDir(Point head, Point near) {
        if (head.x == near.x) {
            if (head.y - near.y > 0) {
                if (direction != SnakeModel.Direction.DOWN) {
                    direction = SnakeModel.Direction.UP;
                    return;
                } else {
                    direction = SnakeModel.Direction.LEFT;
                    return;
                }
            } else {
                if (direction != SnakeModel.Direction.UP) {
                    direction = SnakeModel.Direction.DOWN;
                    return;
                } else {
                    direction = SnakeModel.Direction.LEFT;
                    return;
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
                    return;
                }
            } else {
                if (direction != SnakeModel.Direction.LEFT) {
                    direction = SnakeModel.Direction.RIGHT;
                    return;
                } else {
                    direction = SnakeModel.Direction.DOWN;
                    return;
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

    /**
     * Attempts to eat an apple at head position.
     *
     * @param apple Mutable list of apples
     * @return true if apple was consumed
     */
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
