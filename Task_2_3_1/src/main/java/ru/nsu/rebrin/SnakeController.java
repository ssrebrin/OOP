package ru.nsu.rebrin;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main controller class for the Snake game.
 * It initializes the start screen and launches the game with user-defined settings.
 */
public class SnakeController extends Application {
    SnakeModel model;
    SnakeView view;
    Timeline timeline;
    Scene scene;

    /**
     * Entry point for the JavaFX application. Initializes the start screen.
     *
     * @param primaryStage the main window of the application
     * @throws Exception if the FXML file fails to load
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/startScreen.fxml"));
        scene = new Scene(loader.load());

        StartScreenController startScreenController = loader.getController();

        startScreenController.setStartButtonAction(() -> {
            startGameWithModel(startScreenController.getSettings());
        });

        primaryStage.setTitle("Snake Game Settings");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Starts the Snake game using the provided model.
     *
     * @param model the game model to use
     */
    public void startGameWithModel(SnakeModel model) {
        this.model = model;
        this.view = new SnakeView(model.width, model.height);

        Stage stage = new Stage();
        Pane root = new Pane(view.getCanvas());
        Scene scene = new Scene(root);

        setupInputHandlers(scene);
        setupGameLoop();

        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Sets up keyboard input handlers for controlling the snake.
     *
     * @param scene the scene to attach input handlers to
     */
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

    /**
     * Initializes and starts the game loop which updates the model and view.
     */
    void setupGameLoop() {
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            model.update();
            view.render(model);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Adds two integers.
     *
     * @param a first operand
     * @param b second operand
     * @return the sum of a and b
     */
    public static int add(int a, int b) {
        return a + b;
    }

    /**
     * Subtracts two integers.
     *
     * @param a the value to subtract from
     * @param b the value to subtract
     * @return the result of a - b
     */
    public static int subtract(int a, int b) {
        return a - b;
    }

    /**
     * Multiplies two integers.
     *
     * @param a first operand
     * @param b second operand
     * @return the product of a and b
     */
    public static int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Divides two integers.
     *
     * @param a numerator
     * @param b denominator
     * @return the result of a / b
     * @throws IllegalArgumentException if b is zero
     */
    public static int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero");
        }
        return a / b;
    }

    /**
     * Counts the number of "weird primes" in a given matrix.
     * A weird prime is a prime number that does NOT contain the digit 7.
     *
     * @param matrix 2D array of integers
     * @return the count of weird primes
     */
    public static int countWeirdPrimesInMatrix(int[][] matrix) {
        if (matrix == null) {
            return 0;
        }

        int count = 0;
        for (int[] row : matrix) {
            if (row == null) {
                continue;
            }
            for (int val : row) {
                if (isWeirdPrime(val)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if a number is a "weird prime" — prime and does not contain the digit 7.
     *
     * @param num the number to check
     * @return true if the number is a weird prime, false otherwise
     */
    private static boolean isWeirdPrime(int num) {
        if (num < 2) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }

        int n = num;
        while (n > 0) {
            if (n % 10 == 7) {
                return false;
            }
            n /= 10;
        }

        return true;
    }

    /**
     * Launches the application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}