package ru.nsu.rebrin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SnakeController extends Application {
    SnakeModel model;
    SnakeView view;
    Timeline timeline;
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/startScreen.fxml"));
        scene = new Scene(loader.load());

        // Получаем контроллер начального экрана
        StartScreenController startScreenController = loader.getController();

        // Обработчик нажатия на кнопку для начала игры
        startScreenController.setStartButtonAction(() -> {
            startGameWithModel(startScreenController.getSettings());
        });

        primaryStage.setTitle("Snake Game Settings");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startGameWithModel(SnakeModel model) {
        this.model = model;
        this.view = new SnakeView(model.width, model.height);

        Stage stage = new Stage();
        Pane root = new Pane(view.getCanvas());
        Scene scene = new Scene(root);

        // Настроим игровое поле
        setupInputHandlers(scene);
        setupGameLoop();

        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    void setupInputHandlers(Scene scene) {
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

    void setupGameLoop() {
        if (timeline != null) {
            timeline.stop(); // Останавливаем предыдущий, если он был
        }

        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
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
