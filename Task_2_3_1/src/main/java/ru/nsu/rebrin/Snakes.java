package ru.nsu.rebrin;

import java.util.LinkedList;
import java.util.List;

/**
 * Manages a list of snakes and their interactions.
 */
public class Snakes {
    List<Snake> snakes;

    /**
     * Constructs an empty snake list.
     */
    public Snakes() {
        snakes = new LinkedList<>();
    }

    /**
     * Constructs snakes from given starting points.
     *
     * @param starts list of starting points for each snake
     */
    public Snakes(List<Point> starts) {
        snakes = new LinkedList<>();
        for (Point p : starts) {
            snakes.add(new Snake(p));
        }
    }

    /**
     * Checks if the given point collides with any snake.
     *
     * @param p the point to check
     * @return true if a collision occurred
     */
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

    /**
     * Checks if any snake collided with itself.
     */
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

    /**
     * Checks for collisions between all snakes.
     */
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

    /**
     * Checks for collisions with snakes from another group.
     *
     * @param snakess another group of snakes
     */
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

    /**
     * Checks if any snake's head collided with a list of points.
     *
     * @param snakess list of points
     */
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

    /**
     * Checks if any snakes ate apples.
     *
     * @param apple list of apple positions
     * @return number of apples eaten
     */
    public int checkApple(List<Point> apple) {
        int cnt = 0;
        for (Snake snake : snakes) {
            if (snake.alive && snake.eatApple(apple)) {
                cnt++;
            }
        }
        return cnt;
    }


}
