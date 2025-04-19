package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class SnakeViewTest extends ApplicationTest {
    private SnakeView snakeView;
    private SnakeModel snakeModel;

    @Override
    public void start(Stage stage) {
        snakeModel = new SnakeModel();
        snakeView = new SnakeView(SnakeModel.WIDTH, SnakeModel.HEIGHT);
    }

    @Test
    void testCanvasSize() {
        Canvas canvas = snakeView.getCanvas();
        assertEquals(SnakeModel.WIDTH * 20, canvas.getWidth());
        assertEquals(SnakeModel.HEIGHT * 20, canvas.getHeight());
    }

    @Test
    void testRenderInitialState() {
        assertDoesNotThrow(() -> snakeView.render(snakeModel));
    }

    @Test
    void testRenderSnakeGrows() {
        int initialLength = snakeModel.getSnake().size();
        snakeModel.setPaused(false);
        snakeModel.setDirection(SnakeModel.Direction.RIGHT);
        snakeModel.update();
        assertDoesNotThrow(() -> snakeView.render(snakeModel));
        assertTrue(snakeModel.getSnake().size() >= initialLength);
    }
}
