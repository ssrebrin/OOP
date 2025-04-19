package ru.nsu.rebrin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SnakeController extends Application {
    SnakeModel model;
    SnakeView view;

    @Override
    public void start(Stage primaryStage) {
        model = new SnakeModel();
        view = new SnakeView(SnakeModel.WIDTH, SnakeModel.HEIGHT);

        Pane root = new Pane(view.getCanvas());
        Scene scene = new Scene(root);

        setupInputHandlers(scene);
        setupGameLoop();

        primaryStage.setTitle("Snake Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void setupInputHandlers(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.UP && (model.getLength() == 1
                    || model.getDirection() != SnakeModel.Direction.DOWN)) {
                model.setDirection(SnakeModel.Direction.UP);
                model.click();
            }
            if (key == KeyCode.DOWN && (model.getLength() == 1
                    || model.getDirection() != SnakeModel.Direction.UP)) {
                model.setDirection(SnakeModel.Direction.DOWN);
                model.click();
            }
            if (key == KeyCode.LEFT && (model.getLength() == 1
                    || model.getDirection() != SnakeModel.Direction.RIGHT)) {
                model.setDirection(SnakeModel.Direction.LEFT);
                model.click();
            }
            if (key == KeyCode.RIGHT && (model.getLength() == 1
                    || model.getDirection() != SnakeModel.Direction.LEFT)) {
                model.setDirection(SnakeModel.Direction.RIGHT);
                model.click();
            }
            if (key == KeyCode.ESCAPE) {
                model.setPaused(true);
            }
        });
    }

    private void setupGameLoop() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            model.update();
            view.render(model);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}