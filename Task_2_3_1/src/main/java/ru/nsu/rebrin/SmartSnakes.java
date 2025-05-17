package ru.nsu.rebrin;

import java.util.LinkedList;
import java.util.List;

/**
 * Smart snakes.
 */
public class SmartSnakes extends Snakes {
    /**
     * Init.
     *
     * @param starts - start points
     */
    public SmartSnakes(List<Point> starts) {
        super();
        snakes = new LinkedList<>();
        for (Point p : starts) {
            snakes.add(new SmartSnake(p));
        }
    }
}
