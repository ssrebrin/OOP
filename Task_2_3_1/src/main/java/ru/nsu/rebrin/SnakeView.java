package ru.nsu.rebrin;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SnakeView {
    private static final int TILE_SIZE = 20;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final Image lose = new Image("Blood-Stain-PNG-Clipart-Background.png");
    private final Image screenWin = new Image("win.png");

    public SnakeView(int width, int height) {
        canvas = new Canvas(width * TILE_SIZE, height * TILE_SIZE);
        gc = canvas.getGraphicsContext2D();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void render(SnakeModel model) {
        clear();
        drawApple(model.getApple());
        if (model.getSmartSnake() != null) {
            for (Snake s : model.getSmartSnake()) {
                drawSnake(s.points, Color.CYAN);
            }
        }
        if(model.getStupidSnake() != null) {
            for (Snake s : model.getStupidSnake()) {
                drawSnake(s.points, Color.BLUE);
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

    private void clear() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawSnake(LinkedList<Point> snake, Color color) {
        gc.setFill(color);
        for (Point p : snake) {
            gc.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        }
    }

    private void drawApple(List<Point> apple) {
        if (apple == null) {
            return;
        }
        for (Point p : apple) {
            gc.setFill(Color.RED);
            gc.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        }
    }

    public static List<Rectangle> getSnakeRectangles(LinkedList<Point> snake) {
        final int TILE_SIZE = 20;
        List<Rectangle> rects = new ArrayList<>();
        for (Point p : snake) {
            rects.add(new Rectangle(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1));
        }
        return rects;
    }

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
                // Игнорируем несуществующие направления
            }
        }

        // Добавляем бесполезную задержку для "реализма"
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return vowelCounts;
    }
}