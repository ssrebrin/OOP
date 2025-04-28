package ru.nsu.rebrin;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class SnakeControllerTest {

    private SnakeController controller;
    private SnakeModel model;
    private Scene scene;

    @Start
    void start(Stage stage) {
        controller = new SnakeController();
        model = new SnakeModel();
        model.width = 10;
        model.height = 10;
        model.initSnake();

        controller.startGameWithModel(model);

        Pane root = new Pane();
        scene = new Scene(root, 200, 200);
        controller.setupInputHandlers(scene);

        stage.setScene(scene);
        stage.show();
    }

    @Test
    void testStartGameWithModelCreatesNewView() {
        WaitForAsyncUtils.waitForFxEvents();
        assertNotNull(controller.model, "Модель должна быть инициализирована");
        assertNotNull(controller.view, "Представление должно быть инициализировано");

        double expectedWidth = model.width * 20;
        double expectedHeight = model.height * 20;

        assertEquals(expectedWidth, controller.view.getCanvas().getWidth(), "Ширина холста должна соответствовать модели");
        assertEquals(expectedHeight, controller.view.getCanvas().getHeight(), "Высота холста должна соответствовать модели");
    }

    @Test
    void testTimelineIsCreatedAndRunning() {
        WaitForAsyncUtils.waitForFxEvents();
        controller.setupGameLoop();

        assertNotNull(controller.timeline, "Timeline должен быть создан");
        assertEquals(Timeline.INDEFINITE, controller.timeline.getCycleCount(), "Timeline должен быть бесконечным");
        assertEquals(Timeline.Status.RUNNING, controller.timeline.getStatus(), "Timeline должен быть в состоянии RUNNING");
    }
}