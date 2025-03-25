package ru.nsu.rebrin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends Application {
    private static final int TILE_SIZE = 20;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 15;
    private Direction direction = Direction.RIGHT;
    private LinkedList<Point> snake = new LinkedList<>();
    private boolean running = true;
    private boolean stop = true;
    Point prev = new Point(0, 0);
    Random random = new Random();
    Point apple = new Point(0, random.nextInt(HEIGHT));

    public static void main(String[] args) {
        launch(args);
    }

    public void collision() {
        if (snake.getFirst().equals(apple)) {
            snake.add(prev);
        }
        while (snake.stream().anyMatch(point -> point.equals(apple))) apple = new Point(random.nextInt(WIDTH), random.nextInt(HEIGHT));
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        // Инициализация змейки (3 сегмента для начала)
        for (int i = 0; i < 3; i++) {
            snake.add(new Point(WIDTH / 2 - i, HEIGHT / 2));
        }

        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.UP && direction != Direction.DOWN) {
                stop = false;
                direction = Direction.UP;
            }
            if (key == KeyCode.DOWN && direction != Direction.UP) {
                stop = false;
                direction = Direction.DOWN;
            }
            if (key == KeyCode.LEFT && direction != Direction.RIGHT) {
                direction = Direction.LEFT;
                stop = false;
            }
            if (key == KeyCode.RIGHT && direction != Direction.LEFT) {
                direction = Direction.RIGHT;
                stop = false;
            }
            if (key == KeyCode.ESCAPE) stop = true;
        });

        // Начальная отрисовка
        draw(gc);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            move(gc);
            draw(gc);  // Явная перерисовка после каждого движения
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void move(GraphicsContext gc) {
        if (!running) return;
        if (stop) return;

        Point head = snake.getFirst();
        Point newHead = switch (direction) {
            case UP -> new Point(head.x, head.y - 1);
            case DOWN -> new Point(head.x, head.y + 1);
            case LEFT -> new Point(head.x - 1, head.y);
            case RIGHT -> new Point(head.x + 1, head.y);
        };

        // Проверка границ
        if (newHead.x < 0 || newHead.x >= WIDTH || newHead.y < 0 || newHead.y >= HEIGHT) {
            running = false;
            return;
        }

        snake.addFirst(newHead);
        prev = snake.removeLast();
        collision();
    }

    private void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);

        gc.setFill(Color.GREEN);
        for (Point p : snake) {
            gc.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        }

        gc.setFill(Color.RED);
        gc.fillRect(apple.x * TILE_SIZE, apple.y * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
    }

    private enum Direction { UP, DOWN, LEFT, RIGHT }
    private record Point(int x, int y) {}
}