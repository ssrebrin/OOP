package ru.nsu.rebrin;

import javafx.application.Platform;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


//@Disabled("JavaFX tests don't run properly in headless environments")
@ExtendWith(ApplicationExtension.class)
class SnakeControllerTest {

    private SnakeController controller;
    private SnakeModel model;
    private Scene scene;

    @BeforeAll
    static void setHeadless() {
        System.setProperty("java.awt.headless", "true");
    }

    @BeforeEach
    void setUp() throws Exception {
        controller = new SnakeController();
        model = new SnakeModel(); // например, поле 10x10
        model.width = 10;
        model.height = 10;
        model.initSnake();
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller = new SnakeController();
            controller.startGameWithModel(model);

            Pane root = new Pane();
            scene = new Scene(root, 200, 200);
            controller.setupInputHandlers(scene);

            latch.countDown();
        });

        // Ждём пока код в runLater выполнится
        assertTrue(latch.await(5, TimeUnit.SECONDS));
    }

    @Test
    void testStartGameWithModelCreatesNewView() {
        Platform.runLater(() -> {
            assertNotNull(controller.model);
            assertNotNull(controller.view);

            double expectedWidth = controller.model.width * 20;
            double expectedHeight = controller.model.height * 20;

            assertEquals(expectedWidth, controller.view.getCanvas().getWidth());
            assertEquals(expectedHeight, controller.view.getCanvas().getHeight());
        });
    }


    @Test
    void testTimelineIsCreatedAndRunning() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            controller.setupGameLoop();

            assertNotNull(controller.timeline, "Timeline должен быть создан");
            assertEquals(Timeline.INDEFINITE, controller.timeline.getCycleCount(), "Timeline должен быть бесконечным");
            assertEquals(Timeline.Status.RUNNING, controller.timeline.getStatus(), "Timeline должен быть в состоянии RUNNING");

            latch.countDown();
        });

        // Ждём выполнения runLater
        assertTrue(latch.await(5, TimeUnit.SECONDS), "Тест должен завершиться за 5 секунд");
    }

    @Test
    void testStartGameWithModelCreatesNewViewv() {
        Platform.runLater(() -> {
            assertNotNull(controller.model);
            assertNotNull(controller.view);
            assertEquals(200, controller.view.getCanvas().getWidth());
            assertEquals(200, controller.view.getCanvas().getHeight());
        });
    }
}
