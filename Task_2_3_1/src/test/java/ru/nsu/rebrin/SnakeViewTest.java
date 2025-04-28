package ru.nsu.rebrin;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeViewTest {

    private SnakeView view;
    private SnakeModel model;

    @BeforeAll
    static void setHeadless() {
        System.setProperty("java.awt.headless", "true");
    }

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
            model.initSnake();
            assertDoesNotThrow(() -> view.render(model));
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testRenderGameOverScreenDoesNotCrash() {
        Platform.runLater(() -> {
            model.setRunning(false);
            model.setWin(false);
            assertDoesNotThrow(() -> view.render(model));
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testRenderWinScreenDoesNotCrash() {
        Platform.runLater(() -> {
            model.setRunning(false);
            model.setWin(true);
            assertDoesNotThrow(() -> view.render(model));
        });
        WaitForAsyncUtils.waitForFxEvents();
    }
}
