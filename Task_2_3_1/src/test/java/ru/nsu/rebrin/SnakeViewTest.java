package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class SnakeViewTest {

    private SnakeView view;
    private SnakeModel model;

    @Start
    void start(Stage stage) {
        view = new SnakeView(10, 10);
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
        model.initSnake();
        assertDoesNotThrow(() -> view.render(model));
    }

    @Test
    void testRenderGameOverScreenDoesNotCrash() {
        model.setRunning(false);
        model.setWin(false);
        assertDoesNotThrow(() -> view.render(model));
    }

    @Test
    void testRenderWinScreenDoesNotCrash() {
        model.setRunning(false);
        model.setWin(true);
        assertDoesNotThrow(() -> view.render(model));
    }
}