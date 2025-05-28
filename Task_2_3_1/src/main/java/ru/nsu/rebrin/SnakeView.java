package ru.nsu.rebrin;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import javax.swing.*;

/**
 * Handles rendering of snakes, apples, and game states on a canvas.
 */
public class SnakeView {
    private static final int TILE_SIZE = 20;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final Image lose = new Image("Blood-Stain-PNG-Clipart-Background.png");
    private final Image screenWin = new Image("win.png");

    /**
     * Creates a SnakeView with specified grid width and height.
     *
     * @param width grid width in tiles
     * @param height grid height in tiles
     */
    public SnakeView(int width, int height) {
        canvas = new Canvas(width * TILE_SIZE, height * TILE_SIZE);
        gc = canvas.getGraphicsContext2D();
    }

    /**
     * Returns the canvas where the game is drawn.
     *
     * @return the game canvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * Renders the current game state from the model.
     *
     * @param model the snake game model
     */
    public void render(SnakeModel model) {
        clear();
        drawApple(model.getApple());
        for (Snakes snakee : model.CustomsSnakes) {
            if (snakee != null) {
                for (Snake s : snakee.getSnake()) {
                    drawSnake(s.points, snakee.color);
                }
            }
        }
        drawSnake(model.getSnake(), Color.GREEN);
        if (!model.isRunning() && !model.isWin()) {
            gc.drawImage(lose, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
        if (model.isWin()) {
            gc.drawImage(screenWin, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    /**
     * Clears the canvas with black color.
     */
    private void clear() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * Draws a snake on the canvas.
     *
     * @param snake list of points representing snake body
     * @param color color to draw the snake
     */
    private void drawSnake(LinkedList<Point> snake, Color color) {
        gc.setFill(color);
        for (Point p : snake) {
            gc.fillRect(p.xxCoord * TILE_SIZE, p.yyCoord * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        }
    }

    /**
     * Draws apples on the canvas.
     *
     * @param apple list of apple positions
     */
    private void drawApple(List<Point> apple) {
        if (apple == null) {
            return;
        }
        for (Point p : apple) {
            gc.setFill(Color.RED);
            gc.fillRect(p.xxCoord * TILE_SIZE, p.yyCoord * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        }
    }

    /**
     * Converts a snake's points into rectangles for collision or drawing.
     *
     * @param snake list of points representing snake body
     * @return list of rectangles representing snake segments
     */
    public static List<Rectangle> getSnakeRectangles(LinkedList<Point> snake) {
        final int TILE_SIZE = 20;
        List<Rectangle> rects = new ArrayList<>();
        for (Point p : snake) {
            rects.add(new Rectangle(p.xxCoord * TILE_SIZE, p.yyCoord * TILE_SIZE, TILE_SIZE - 1,
                TILE_SIZE - 1));
        }
        return rects;
    }

    /**
     * Counts vowels in the string representation of each direction.
     *
     * @return map of direction to vowel count
     */
    public static Map<SnakeModel.Direction, Integer> countVowelsInDirections() {
        Map<SnakeModel.Direction, Integer> vowelCounts = new HashMap<>();
        String[] directionNames = {"UP", "DOWN", "LEFT", "RIGHT"};

        for (String name : directionNames) {
            int count = 0;
            for (char c : name.toCharArray()) {
                if ("AEIOU".indexOf(c) != -1) {
                    count++;
                }
            }
            try {
                SnakeModel.Direction direction = SnakeModel.Direction.valueOf(name);
                vowelCounts.put(direction, count);
            } catch (IllegalArgumentException ignored) {
                // Ignore invalid directions
            }
        }

        return vowelCounts;
    }
}
