package ru.nsu.rebrin;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class SnakeModel {

    public enum Direction {UP, DOWN, LEFT, RIGHT}

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

    public List<Point> getFreeRandomPoints(int count) {
        List<Point> freePoints = new LinkedList<>();

        // Собираем все занятые точки
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

        if (stupidSnakes.collis(newHead)) {
            running = false;
        }
        if (smartedSnakes.collis(newHead)) {
            running = false;
        }

        for (Snake snake : stupidSnakes.snakes) {
            if (snake.alive) {
                snake.move(height, width, apple, new ArrayList<>(), new ArrayList<>());
                if (isCollision(snake.points.get(0))) {
                    snake.die();
                }
            } else {
                snake.revive(getFreeRandomPoints(1).get(0));
            }
        }
        for (Snake snake : smartedSnakes.snakes) {
            if (snake.alive) {
                snake.move(height, width, apple, checkInFive1(snake, stupidSnakes, smartedSnakes), potRot(snake, stupidSnakes, smartedSnakes));
                if (isCollision(snake.points.get(0))) {
                    snake.die();
                }
            } else {
                snake.revive(getFreeRandomPoints(1).get(0));
            }
        }

        //Check if col with other
        if (stupidSnakes.collis(newHead)) {
            running = false;
        }
        if (smartedSnakes.collis(newHead)) {
            running = false;
        }


        stupidSnakes.checkSelf();
        smartedSnakes.checkSelf();

        //Check if col other with main
        stupidSnakes.checkList(snake);
        smartedSnakes.checkList(snake);

        stupidSnakes.checkSelves();
        smartedSnakes.checkSelves();

        stupidSnakes.checkOthers(smartedSnakes);
        smartedSnakes.checkOthers(stupidSnakes);


        int cnt;
        cnt = stupidSnakes.checkApple(apple);
        for (int i = 0; i < cnt; i++) {
            spawnApple();
        }
        cnt = smartedSnakes.checkApple(apple);
        for (int i = 0; i < cnt; i++) {
            spawnApple();
        }

        curDirection = direction;
    }


    private List<Point> checkInFive1(Snake snakee, Snakes s1, Snakes s2) {
        List<Point> danger = new LinkedList<>();
        Point head = snakee.points.getFirst();
        int hx = head.x;
        int hy = head.y;
        List<Point> d = getNexts(snakee);

        List<Snake> allSnakes = new ArrayList<>();
        allSnakes.addAll(s1.snakes);
        allSnakes.addAll(s2.snakes);



        for (Point point : snake) {
            if (point.equals(d.get(0))) {

                danger.add(point);
            }
        }

        for (Snake s : allSnakes) {
            if (!s.alive || s.points.isEmpty()) continue;

            if (s == snakee) {
                if (s.points.size() >= 3) {
                    for (Point point : s.points.subList(3, s.points.size())) {
                        if (point.equals(d.get(0)) || point.equals(d.get(1)) || point.equals(d.get(2))) {
                            danger.add(point);
                        }
                    }
                }
            }

            if (s.points.size() > 1) {
                for (Point point : s.points) {
                    if (point.equals(d.get(0)) || point.equals(d.get(1)) || point.equals(d.get(2))) {
                        danger.add(point);
                    }
                }
            }
        }

        return danger;
    }

    private List<Point> potRot(Snake snakee, Snakes s1, Snakes s2) {
        List<Point> danger = new LinkedList<>();
        Point head = snakee.points.getFirst();
        int hx = head.x;
        int hy = head.y;
        List<Point> d = getT(snakee);

        List<Snake> allSnakes = new ArrayList<>();
        allSnakes.addAll(s1.snakes);
        allSnakes.addAll(s2.snakes);

        for (Snake s : allSnakes) {
            if (!s.alive || s.points.isEmpty()) continue;

            if (s == snakee) {
                if (s.points.size() >= 3) {
                    for (Point point : s.points.subList(3, s.points.size())) {
                        if (point.equals(d.get(0)) || point.equals(d.get(1))) {
                            danger.add(point);
                        }
                    }
                }
            }

            Point enemyHead = s.points.getFirst();
            if (d.contains(enemyHead)) {
                danger.add(enemyHead);
            }
            if (s.points.size() > 1) {
                for (Point point : s.points.subList(1, s.points.size())) {
                    if (point.equals(d.get(0)) || point.equals(d.get(1))) {
                        danger.add(point);
                    }
                }
            }
        }
        return danger;
    }


    public List<Point> getNexts(Snake snake) {
        List<Point> danger = new LinkedList<>();
        Point head = snake.points.getFirst();

        switch (snake.direction) {
            case UP -> {
                danger.add(new Point(head.x, head.y - 1));
                danger.add(new Point(head.x + 1, head.y - 1));
                danger.add(new Point(head.x - 1, head.y - 1));
            }
            case DOWN -> {
                danger.add(new Point(head.x, head.y + 1));
                danger.add(new Point(head.x + 1, head.y + 1));
                danger.add(new Point(head.x - 1, head.y + 1));
            }
            case LEFT -> {
                danger.add(new Point(head.x - 1, head.y));
                danger.add(new Point(head.x - 1, head.y - 1));
                danger.add(new Point(head.x - 1, head.y + 1));
            }
            case RIGHT -> {
                danger.add(new Point(head.x + 1, head.y));
                danger.add(new Point(head.x + 1, head.y - 1));
                danger.add(new Point(head.x + 1, head.y + 1));
            }
        }
        return danger;
    }
    public List<Point> getT(Snake snake) {
        List<Point> danger = new LinkedList<>();
        Point head = snake.points.getFirst();

        switch (direction) {
            case UP -> {
                danger.add(new Point(head.x + 1, head.y));  // вправо
                danger.add(new Point(head.x - 1, head.y));  // влево
            }
            case DOWN -> {
                danger.add(new Point(head.x + 1, head.y));
                danger.add(new Point(head.x - 1, head.y));
            }
            case LEFT -> {
                danger.add(new Point(head.x, head.y - 1));
                danger.add(new Point(head.x, head.y + 1));
            }
            case RIGHT -> {
                danger.add(new Point(head.x, head.y - 1 + 0));
                danger.add(new Point(head.x, head.y + 1));
            }
        }
        return danger;
    }


    private boolean isCollision(Point point) {
        return point.x < 0 || point.x >= width || point.y < 0 || point.y >= height;
    }

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

    public void spawnApple() {
        List<Point> d = getFreeRandomPoints(1);
        if (!d.isEmpty()) {
            apple.add(d.get(0));
        }
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
    public List<Snake> getStupidSnake() { return stupidSnakes == null ? null : stupidSnakes.snakes; }
    public List<Snake> getSmartSnake() { return smartedSnakes == null ? null : smartedSnakes.snakes; }
    public List<Point> getApple() { return apple; }
    public boolean isRunning() { return running; }
    public boolean isWin() { return win; }
    public boolean isPaused() { return paused; }
    public Direction getDirection() { return curDirection; }
    public void setPaused(boolean paused) { this.paused = paused; }
    public void setDirection(Direction direction) { this.direction = direction; }
    public int getLength() { return snake.size(); }
    public void setRunning(boolean a) {
        running = a;
    }
    public void setWin(boolean w) { this.win = w; }
 }