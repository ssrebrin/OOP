package ru.nsu.rebrin;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SnakeModel {

    public enum Direction { UP, DOWN, LEFT, RIGHT }

    static final int WIDTH = 20;
    static final int HEIGHT = 15;
    static final int WIN = 10;
    static final int INITLEN = 1;
    static final int CNTAPPLE = 3;

    private Direction direction = Direction.RIGHT;
    private Direction curDirection;
    private LinkedList<Point> snake;
    private boolean running = true;
    private boolean paused = true;
    private boolean win = false;
    private Point prevTail;
    private final Random random = new Random();
    private List<Point> apple;

    public SnakeModel() {
        initSnake();
    }

    private void initSnake() {
        snake = new LinkedList<>();
        curDirection = Direction.RIGHT;
        for (int i = 0; i < INITLEN; i++) {
            snake.add(new Point(WIDTH / 2 - i, HEIGHT / 2));
        }
        apple = new LinkedList<>();
        for (int i = 0; i < CNTAPPLE; i++) {
            spawnApple();
        }
    }

    public void update() {
        if (!running || paused) return;

        Point head = snake.getFirst();
        Point newHead = switch (direction) {
            case UP -> new Point(head.x, head.y - 1);
            case DOWN -> new Point(head.x, head.y + 1);
            case LEFT -> new Point(head.x - 1, head.y);
            case RIGHT -> new Point(head.x + 1, head.y);
        };

        if (isCollision(newHead)) {
            running = false;
            return;
        }

        snake.addFirst(newHead);
        prevTail = snake.removeLast();
        checkApple();

        if (snake.size() == WIN) {
            running = false;
            win = true;
        }
        for (Point point : snake.subList(1, snake.size())) {
            if (newHead.equals(point)) {
                running = false;
                break;
            }
        }
        curDirection = direction;
    }

    private boolean isCollision(Point point) {
        return point.x < 0 || point.x >= WIDTH || point.y < 0 || point.y >= HEIGHT;
    }

    private void checkApple() {
        Point head = snake.getFirst();
        for (Point point : apple) {
            if (snake.getFirst().equals(point)) {
                snake.add(prevTail);
                apple.remove(point);
                spawnApple();
                break;
            }
        }
    }

    public void spawnApple() {
        Point newApple;
        do {
            newApple = new Point(random.nextInt(WIDTH), random.nextInt(HEIGHT));
        } while (snake.contains(newApple));
        apple.add(newApple);
    }

    public void click(){
        if (paused) {
            paused = false;
            return;
        }
        if (!running) {
            running = true;
            paused = true;
            win = false;
            initSnake();
            return;
        }
    }

    public LinkedList<Point> getSnake() { return snake; }
    public List<Point> getApple() { return apple; }
    public boolean isRunning() { return running; }
    public boolean isWin() { return win; }
    public boolean isPaused() { return paused; }
    public Direction getDirection() { return curDirection; }
    public void setPaused(boolean paused) { this.paused = paused; }
    public void setDirection(Direction direction) { this.direction = direction; }
    public int getLength() { return snake.size(); }
}