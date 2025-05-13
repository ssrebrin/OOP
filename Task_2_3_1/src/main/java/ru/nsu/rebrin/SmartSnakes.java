package ru.nsu.rebrin;

import java.util.LinkedList;
import java.util.List;

public class SmartSnakes extends Snakes {
    public SmartSnakes(List<Point> starts) {
        super();
        snakes = new LinkedList<>();
        for (Point p : starts) {
            snakes.add(new SmartSnake(p));
        }
    }
}
