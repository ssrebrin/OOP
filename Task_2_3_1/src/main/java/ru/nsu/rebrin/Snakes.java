package ru.nsu.rebrin;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Snakes {
    List<Snake> snakes;

    public Snakes() {
        snakes = new LinkedList<>();
    }

    public Snakes(List<Point> starts) {
        snakes = new LinkedList<>();
        for (Point p : starts) {
            snakes.add(new Snake(p));
        }
    }

    public boolean collis(Point p) {
        for (Snake snake : snakes) {
            if (!snake.alive) {
                continue;
            }
            if (snake.points.get(0).equals(p)) {
                snake.die();
                return true;
            }
            if (snake.points.size() > 1) {
                for (Point pp : snake.points.subList(1, snake.points.size())) {
                    if (p.equals(pp)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void checkSelf() {
        for (Snake snake : snakes) {
            if (!snake.alive) {
                continue;
            }
            Point head = snake.points.get(0);
            if (snake.points.size() > 1) {
                for (Point pp : snake.points.subList(1, snake.points.size())) {
                    if (head.equals(pp)) {
                        snake.die();
                    }
                }
            }
        }
    }

    public void checkSelves() {
        for (Snake snake : snakes) {
            if (!snake.alive) {
                continue;
            }
            Point head = snake.points.get(0);
            for (Snake snake2 : snakes) {
                if (snake == snake2 || !snake2.alive) {
                    continue;
                }
                if (head.equals(snake2.points.get(0))) {
                    snake.die();
                    snake2.die();
                    continue;
                }
                if (snake2.points.size() >= 2) {
                    for (Point pp : snake2.points.subList(1, snake2.points.size())) {
                        if (head.equals(pp)) {
                            snake.die();
                            break;
                        }
                    }
                }
            }
        }
    }

    public void checkOthers(Snakes snakess) {
        for (Snake snake : snakes) {
            if (!snake.alive) {
                continue;
            }
            Point head = snake.points.get(0);
            for (Snake snake2 : snakess.snakes) {
                if (!snake2.alive) {
                    continue;
                }
                if (head.equals(snake2.points.get(0))) {
                    snake.die();
                    snake2.die();
                }
                if (snake2.points.size() >= 2) {
                    for (Point p : snake2.points.subList(1, snake2.points.size())) {
                        if (head.equals(p)) {
                            snake.die();
                            break;
                        }
                    }
                }
            }
        }
    }

    public void checkList(List<Point> snakess) {
        for (Snake snake : snakes) {
            if (!snake.alive) {
                continue;
            }
            Point head = snake.points.get(0);
            if (snakess.size() > 1) {
                for (Point p : snakess.subList(1, snakess.size())) {
                    if (head.equals(p)) {
                        snake.die();
                        break;
                    }
                }
            }
        }
    }

    public int checkApple(List<Point> apple){
        int cnt = 0;
        for (Snake snake : snakes) {
            if (snake.alive && snake.eatApple(apple)) {
                cnt++;
            }
        }
        return cnt;
    }


}
