package ru.nsu.rebrin;

import java.util.List;

/**
 * A smarter implementation of a Snake that chooses the safest and shortest path to an apple
 * while avoiding dangerous areas and self-collision.
 */
public class SmartSnake extends Snake {

    /**
     * Creates a smart snake starting at the specified point.
     *
     * @param start the starting point of the snake
     */
    public SmartSnake(Point start) {
        super(start);
    }

    /**
     * Moves the snake according to the current state of the board, attempting to find
     * the safest and most efficient path to the nearest apple while avoiding.
     *
     * @param height the height of the board
     * @param width  the width of the board
     * @param apples the list of apple positions
     * @param danger the list of dangerous positions to avoid
     */
    @Override
    public void move(int height, int width, List<Point> apples, List<Point> danger) {
        if (points.isEmpty()) return;

        Point head = points.getFirst();

        System.out.print("[" + head.xCoord + ", " + head.yCoord + "]");
        for (Point i : danger) {
            System.out.print(i.xCoord + " " + i.yCoord + "  ");
        }

        List<SnakeModel.Direction> possibleMoves = List.of(
                SnakeModel.Direction.UP,
                SnakeModel.Direction.DOWN,
                SnakeModel.Direction.LEFT,
                SnakeModel.Direction.RIGHT
        );

        SnakeModel.Direction bestMove = null;
        int bestDistance = Integer.MAX_VALUE;
        boolean bestInPot = true; // initially prefer avoiding danger

        for (SnakeModel.Direction dir : possibleMoves) {
            if (isReverse(dir)) continue;

            Point next = nextPoint(head, dir);

            if (!canMoveTo(next, height, width, danger)) continue;

            int distance = distanceToNearest(next, apples);
            boolean inPot = containsPoint(danger, next);

            if (bestMove == null
                || (!inPot && bestInPot)
                || (inPot == bestInPot && distance < bestDistance)) {

                bestMove = dir;
                bestDistance = distance;
                bestInPot = inPot;
            }
        }

        // Fallback: move in any allowed direction that is not a reverse
        if (bestMove == null) {
            for (SnakeModel.Direction dir : possibleMoves) {
                if (isReverse(dir)) continue;
                Point next = nextPoint(head, dir);
                if (isInside(next, height, width)) {
                    bestMove = dir;
                    break;
                }
            }
        }

        // Final fallback: any non-reverse move
        if (bestMove == null) {
            for (SnakeModel.Direction dir : possibleMoves) {
                if (!isReverse(dir)) {
                    bestMove = dir;
                    break;
                }
            }
        }

        if (bestMove != null) {
            direction = bestMove;
            Point newHead = nextPoint(head, direction);
            System.out.println(" Moving to " + newHead.xCoord + " " + newHead.yCoord);
            points.addFirst(newHead);
            prevTail = points.removeLast();
        }
    }

    /**
     * Checks whether the snake can move to the specified position.
     *
     * @param next   the position to move to
     * @param height the board height
     * @param width  the board width
     * @param danger the list of dangerous positions
     * @return true if the position is valid and safe to move into, false otherwise
     */
    private boolean canMoveTo(Point next, int height, int width, List<Point> danger) {
        if (!isInside(next, height, width)) {
            return false;
        }
        if (containsPoint(danger, next)) {
            return false;
        }
        if (points.contains(next)) {
            return false;
        }
        return true;
    }

    /**
     * Checks if a point is within the board boundaries.
     *
     * @param p      the point to check
     * @param height the board height
     * @param width  the board width
     * @return true if inside the board, false otherwise
     */
    private boolean isInside(Point p, int height, int width) {
        return p.xCoord >= 0 && p.xCoord < width && p.yCoord >= 0 && p.yCoord < height;
    }

    /**
     * Checks if the snake can move in the given direction.
     *
     * @param dir    the direction to check
     * @param height the board height
     * @param width  the board width
     * @param danger the list of dangerous positions
     * @return true if movement is possible, false otherwise
     */
    private boolean canMove(SnakeModel.Direction dir, int height, int width, List<Point> danger) {
        Point head = points.getFirst();
        Point next = nextPoint(head, dir);

        if (next.xCoord < 0 || next.xCoord >= width || next.yCoord < 0 || next.yCoord >= height) {
            return false;
        }
        if (containsPoint(danger, next)) {
            return false;
        }
        if (points.contains(next)) {
            return false;
        }

        return true;
    }

    /**
     * Checks if a direction is the reverse of the current direction.
     *
     * @param newDir the new direction to check
     * @return true if newDir is the reverse of the current direction, false otherwise
     */
    private boolean isReverse(SnakeModel.Direction newDir) {
        if (points.size() <= 1) return false;

        switch (direction) {
            case UP: return newDir == SnakeModel.Direction.DOWN;
            case DOWN: return newDir == SnakeModel.Direction.UP;
            case LEFT: return newDir == SnakeModel.Direction.RIGHT;
            case RIGHT: return newDir == SnakeModel.Direction.LEFT;
            default:;
        }
        return false;
    }

    /**
     * Calculates the next point in the given direction from a starting point.
     *
     * @param p   the starting point
     * @param dir the direction to move
     * @return the next point in that direction
     */
    private Point nextPoint(Point p, SnakeModel.Direction dir) {
        switch (dir) {
            case UP: return new Point(p.xCoord, p.yCoord - 1);
            case DOWN: return new Point(p.xCoord, p.yCoord + 1);
            case LEFT: return new Point(p.xCoord - 1, p.yCoord);
            case RIGHT: return new Point(p.xCoord + 1, p.yCoord);
            default:;
        }
        return p; // default fallback
    }

    /**
     * Calculates the Manhattan distance to the nearest target from a given point.
     *
     * @param from    the starting point
     * @param targets the list of target points
     * @return the shortest Manhattan distance to any target
     */
    private int distanceToNearest(Point from, List<Point> targets) {
        int minDist = Integer.MAX_VALUE;
        for (Point t : targets) {
            int dist = Math.abs(from.xCoord - t.xCoord) + Math.abs(from.yCoord - t.yCoord);
            minDist = Math.min(minDist, dist);
        }
        return minDist == Integer.MAX_VALUE ? 0 : minDist;
    }

    /**
     * Checks if the given list contains the specified point.
     *
     * @param list the list of points
     * @param p    the point to search for
     * @return true if the list contains the point, false otherwise
     */
    private boolean containsPoint(List<Point> list, Point p) {
        for (Point q : list) {
            if (q.equals(p)) {
                return true;
            }
        }
        return false;
    }

}
