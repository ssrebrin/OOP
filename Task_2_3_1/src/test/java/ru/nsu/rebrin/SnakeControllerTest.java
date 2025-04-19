package ru.nsu.rebrin;

import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

class SnakeControllerTest extends ApplicationTest {
    private SnakeModel model;

    @Override
    public void start(Stage stage) {
        SnakeController controller = new SnakeController();
        controller.start(stage);
        model = controller.model; // Доступ к модели
    }

    @Test
    void testSnakeMovesRight() {
        KeyCode key = KeyCode.DOWN;
        press(key);
        release(key);
        model.update();

        assertEquals(SnakeModel.Direction.DOWN, model.getDirection());
    }

    @Test
    void testPauseGame() {
        KeyCode key = KeyCode.ESCAPE;
        press(key);
        release(key);

        assertTrue(model.isPaused());
    }
}
