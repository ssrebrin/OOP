package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SnakeModelTest {
    private SnakeModel snakeModel;

    @BeforeEach
    void setUp() {
        snakeModel = new SnakeModel();
    }

    @Test
    void testInitialConditions() {
        assertTrue(snakeModel.isPaused());
        assertTrue(snakeModel.isRunning());
        assertFalse(snakeModel.isWin());
        assertEquals(SnakeModel.Direction.RIGHT, snakeModel.getDirection());
        assertEquals(1, snakeModel.getLength());
        assertEquals(3, snakeModel.getApple().size());
    }

    @Test
    void testCollisionWithWall() {
        snakeModel.setPaused(false);
        snakeModel.setDirection(SnakeModel.Direction.LEFT);
        for (int i = 0; i < SnakeModel.WIDTH / 2 + 1; i++) {
            snakeModel.update();
        }
        assertFalse(snakeModel.isRunning());
    }

    @Test
    void testEatApple() throws InterruptedException {
        int initialLength = snakeModel.getLength();
        for(int i = 0;i<snakeModel.HEIGHT*snakeModel.WIDTH - 4;i++) {
            snakeModel.spawnApple();
        }
        snakeModel.setPaused(false);
        snakeModel.update();

        assertTrue(snakeModel.getLength() > initialLength);
    }
}
