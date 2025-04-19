package ru.nsu.rebrin;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.LinkedList;
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

    public Canvas getCanvas() { return canvas; }

    public void render(SnakeModel model) {
        clear();
        drawSnake(model.getSnake());
        drawApple(model.getApple());
        if (!model.isRunning() && !model.isWin()) {
            gc.drawImage(lose, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
        if(model.isWin()) {
            gc.drawImage(screenWin, 0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    private void clear() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawSnake(LinkedList<Point> snake) {
        gc.setFill(Color.GREEN);
        for (Point p : snake) {
            gc.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        }
    }

    private void drawApple(List<Point> apple) {
        for(Point p : apple) {
            gc.setFill(Color.RED);
            gc.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        }
    }
}