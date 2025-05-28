package ru.nsu.rebrin;

import java.awt.*;
import javafx.scene.paint.Color;
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
    public SmartSnakes(List<Point> starts, int id) {

        super();
        this.id = id;
        color = Color.CYAN;
        List<Snake> a = new LinkedList<>();
        int i = 0;
        for (Point p : starts) {
            a.add(new SmartSnake(p, i++));
        }

        setSnakes(a);
    }
}
