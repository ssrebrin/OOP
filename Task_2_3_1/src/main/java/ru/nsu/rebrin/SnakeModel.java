package ru.nsu.rebrin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * The SnakeModel class handles the game logic for the Snake game,
 * including movement, collision detection, apple spawning, and AI snakes.
 */
public class SnakeModel {

    /**
     * Enum representing possible movement directions for the snake.
     */
    public enum Direction { UP, DOWN, LEFT, RIGHT }

    int width = 20;
    int height = 15;
    int winn = 10;
    int snakeLength = 1;
    int appleCount = 3;
    int smart = 1;
    int stupid = 1;

    private Direction direction = Direction.RIGHT;
    private Direction curDirection;
    private LinkedList<Point> snake;
    private Snakes stupidSnakes = null;
    private Snakes smartedSnakes = null;
    private boolean running = true;
    private boolean paused = true;
    private boolean win = false;
    private Point prevTail;
    private final Random random = new Random();
    private List<Point> apple;

    /**
     * Initializes the main snake, apples, and AI snakes.
     */
    public void initSnake() {
        snake = new LinkedList<>();
        curDirection = Direction.RIGHT;
        for (int i = 0; i < snakeLength; i++) {
            snake.add(new Point(width / 2 - i, height / 2));
        }
        apple = new LinkedList<>();
        for (int i = 0; i < appleCount; i++) {
            spawnApple();
        }

        stupidSnakes = new Snakes(getFreeRandomPoints(stupid));
        smartedSnakes = new SmartSnakes(getFreeRandomPoints(smart));
    }

    /**
     * Returns a list of random free positions not occupied by any object.
     *
     * @param count number of free points to find
     * @return list of available points
     */
    public List<Point> getFreeRandomPoints(int count) {
        List<Point> occupied = new LinkedList<>(snake);
        occupied.addAll(apple);
        if (stupidSnakes != null) {
            for (Snake s : stupidSnakes.snakes) {
                occupied.addAll(s.points);
            }
        }
        if (smartedSnakes != null) {
            for (Snake s : smartedSnakes.snakes) {
                occupied.addAll(s.points);
            }
        }

        List<Point> freePoints = new LinkedList<>();
        int attempts = 0;
        while (freePoints.size() < count && attempts < 1000) {
            Point candidate = new Point(random.nextInt(width), random.nextInt(height));
            if (!occupied.contains(candidate) && !freePoints.contains(candidate)) {
                freePoints.add(candidate);
            }
            attempts++;
        }

        return freePoints;
    }

    /**
     * Updates the game state, moves the snakes, handles collisions and apple consumption.
     */
    public void update() {
        if (!running || paused) {
            return;
        }

        Point head = snake.getFirst();
        Point newHead = switch (direction) {
            case UP -> new Point(head.xxCoord, head.yyCoord - 1);
            case DOWN -> new Point(head.xxCoord, head.yyCoord + 1);
            case LEFT -> new Point(head.xxCoord - 1, head.yyCoord);
            case RIGHT -> new Point(head.xxCoord + 1, head.yyCoord);
        };

        if (isCollision(newHead)) {
            running = false;
            return;
        }

        snake.addFirst(newHead);
        prevTail = snake.removeLast();
        checkApple();

        if (snake.size() == winn) {
            running = false;
            win = true;
        }

        for (Point point : snake.subList(1, snake.size())) {
            if (newHead.equals(point)) {
                running = false;
                break;
            }
        }

        if (stupidSnakes.collis(newHead) || smartedSnakes.collis(newHead)) {
            running = false;
        }

        for (Snake snake : stupidSnakes.snakes) {
            if (snake.alive) {
                snake.move(height, width, apple, new ArrayList<>());
                if (isCollision(snake.points.get(0))) {
                    snake.die();
                }
            } else {
                snake.revive(getFreeRandomPoints(1).get(0));
            }
        }

        for (Snake snake : smartedSnakes.snakes) {
            if (snake.alive) {
                snake.move(height, width, apple, checkInFive1(snake, stupidSnakes, smartedSnakes));
                if (isCollision(snake.points.get(0))) {
                    snake.die();
                }
            } else {
                snake.revive(getFreeRandomPoints(1).get(0));
            }
        }

        stupidSnakes.checkSelf();
        smartedSnakes.checkSelf();

        stupidSnakes.checkList(snake);
        smartedSnakes.checkList(snake);

        stupidSnakes.checkSelves();
        smartedSnakes.checkSelves();

        stupidSnakes.checkOthers(smartedSnakes);
        smartedSnakes.checkOthers(stupidSnakes);

        int cnt = stupidSnakes.checkApple(apple);
        for (int i = 0; i < cnt; i++) {
            spawnApple();
        }

        cnt = smartedSnakes.checkApple(apple);
        for (int i = 0; i < cnt; i++) {
            spawnApple();
        }

        curDirection = direction;
    }

    /**
     * Returns a list of points that are dangerous to step on for a smart snake.
     *
     * @param snakee the smart snake
     * @param s1     first group of snakes
     * @param s2     second group of snakes
     * @return list of dangerous points
     */
    private List<Point> checkInFive1(Snake snakee, Snakes s1, Snakes s2) {
        List<Point> danger = new LinkedList<>();
        Point head = snakee.points.getFirst();
        List<Point> d = getNexts(snakee);

        List<Snake> allSnakes = new ArrayList<>();
        allSnakes.addAll(s1.snakes);
        allSnakes.addAll(s2.snakes);

        for (Point point : snake) {
            if (d.contains(point)) {
                danger.add(point);
            }
        }

        for (Snake s : allSnakes) {
            if (!s.alive || s.points.isEmpty()) {
                continue;
            }

            if (s == snakee && s.points.size() >= 3) {
                for (Point point : s.points.subList(3, s.points.size())) {
                    if (d.contains(point)) {
                        danger.add(point);
                    }
                }
            }

            for (Point point : s.points) {
                if (d.contains(point)) {
                    danger.add(point);
                }
            }
        }

        return danger;
    }

    /**
     * Returns a list of points in front and to the sides of the snake's head based on its dir.
     *
     * @param snake the snake
     * @return list of next possible positions
     */
    public List<Point> getNexts(Snake snake) {
        List<Point> danger = new LinkedList<>();
        Point head = snake.points.getFirst();

        int i = 0;
        switch (snake.direction) {
            case UP -> {
                danger.add(new Point(head.xxCoord, head.yyCoord - 1));
                danger.add(new Point(head.xxCoord + 1, head.yyCoord));
                danger.add(new Point(head.xxCoord - 1, head.yyCoord));
            }
            case DOWN -> {
                danger.add(new Point(head.xxCoord, head.yyCoord + 1));
                danger.add(new Point(head.xxCoord + 1, head.yyCoord));
                danger.add(new Point(head.xxCoord - 1, head.yyCoord));
            }
            case LEFT -> {
                danger.add(new Point(head.xxCoord - 1, head.yyCoord));
                danger.add(new Point(head.xxCoord, head.yyCoord - 1));
                danger.add(new Point(head.xxCoord, head.yyCoord + 1));
            }
            case RIGHT -> {
                danger.add(new Point(head.xxCoord + 1, head.yyCoord));
                danger.add(new Point(head.xxCoord, head.yyCoord - 1));
                danger.add(new Point(head.xxCoord, head.yyCoord + 1));
            }
            default -> {
                i = 0;
            }
        }

        return danger;
    }

    /**
     * Checks if the given point is outside the bounds of the game board.
     *
     * @param point the point to check
     * @return true if collision with wall, false otherwise
     */
    private boolean isCollision(Point point) {
        return point.xxCoord < 0 || point.xxCoord >= width || point.yyCoord < 0
            || point.yyCoord >= height;
    }

    /**
     * Checks if the main snake has eaten an apple and updates the state accordingly.
     */
    private void checkApple() {
        Point head = snake.getFirst();
        for (Point point : apple) {
            if (head.equals(point)) {
                snake.add(prevTail);
                apple.remove(point);
                spawnApple();
                break;
            }
        }
    }

    /**
     * Spawns a new apple at a free location.
     */
    public void spawnApple() {
        List<Point> d = getFreeRandomPoints(1);
        if (!d.isEmpty()) {
            apple.add(d.get(0));
        }
    }

    /**
     * Handles user click — starts, pauses, or resets the game depending on state.
     */
    public void click() {
        if (paused) {
            paused = false;
            return;
        }
        if (!running) {
            running = true;
            paused = true;
            win = false;
            initSnake();
        }
    }

    /**
     * Sets the running state of the game.
     *
     * @return current list of points occupied by the main snake
     */
    public LinkedList<Point> getSnake() {
        return snake;
    }

    /**
     * Sets the running state of the game.
     *
     * @return list of stupid snakes or null if not initialized
     */
    public List<Snake> getStupidSnake() {
        return stupidSnakes == null ? null : stupidSnakes.snakes;
    }

    /**
     * Sets the running state of the game.
     *
     * @return list of smart snakes or null if not initialized
     */
    public List<Snake> getSmartSnake() {
        return smartedSnakes == null ? null : smartedSnakes.snakes;
    }

    /**
     * Sets the running state of the game.
     *
     * @return list of apple positions
     */
    public List<Point> getApple() {
        return apple;
    }

    /**
     * Sets the running state of the game.
     *
     * @return whether the game is currently running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Sets the running state of the game.
     *
     * @return true if player has won
     */
    public boolean isWin() {
        return win;
    }

    /**
     * Sets the running state of the game.
     *
     * @return true if game is paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Sets the running state of the game.
     *
     * @return current confirmed direction
     */
    public Direction getDirection() {
        return curDirection;
    }

    /**
     * Sets the pause state of the game.
     *
     * @param paused true to pause the game
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Sets the desired direction for the main snake.
     *
     * @param direction new direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Sets the running state of the game.
     * @return current length of the snake
     */
    public int getLength() {
        return snake.size();
    }

    /**
     * Sets the running state of the game.
     *
     * @param a true if the game should be running
     */
    public void setRunning(boolean a) {
        running = a;
    }

    /**
     * Sets the win state.
     *
     * @param w true if the player has won
     */
    public void setWin(boolean w) {
        this.win = w;
    }
}
