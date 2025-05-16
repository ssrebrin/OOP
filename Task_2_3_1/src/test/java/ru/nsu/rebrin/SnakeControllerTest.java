package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnakeControllerTest {

    @Test
    void testAddition() {
        assertEquals(5, SnakeController.add(2, 3));
    }

    @Test
    void testSubtraction() {
        assertEquals(1, SnakeController.subtract(4, 3));
    }

    @Test
    void testMultiplication() {
        assertEquals(12, SnakeController.multiply(3, 4));
    }

    @Test
    void testDivision() {
        assertEquals(2, SnakeController.divide(10, 5));
    }

    @Test
    void testDivisionByZeroThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> SnakeController.divide(5, 0));
    }
}