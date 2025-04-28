package ru.nsu.rebrin;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//@Disabled("JavaFX tests don't run properly in headless environments")
public class SnakeViewTest {

    private SnakeView view;
    private SnakeModel model;

    @BeforeEach
    void setUp() {
        view = new SnakeView(10, 10);  // поле 10х10
        model = new SnakeModel();
        model.width = 10;
        model.height = 10;
    }

    @Test
    void testCanvasIsCreatedCorrectly() {
        Canvas canvas = view.getCanvas();
        assertNotNull(canvas);
        assertEquals(10 * 20, canvas.getWidth());
        assertEquals(10 * 20, canvas.getHeight());
    }

    @Test
    void testRenderDoesNotCrash() {
        Platform.runLater(() -> {
            // Добавим одну точку змеи
            model.initSnake();

            assertDoesNotThrow(() -> view.render(model));
        });
    }

    @Test
    void testRenderGameOverScreenDoesNotCrash() {
        Platform.runLater(() -> {
            model.setRunning(false);
            model.setWin(false);

            assertDoesNotThrow(() -> view.render(model));
        });
    }

    @Test
    void testRenderWinScreenDoesNotCrash() {
        Platform.runLater(() -> {
            model.setRunning(false);
            model.setWin(true);

            assertDoesNotThrow(() -> view.render(model));
        });
    }
}
