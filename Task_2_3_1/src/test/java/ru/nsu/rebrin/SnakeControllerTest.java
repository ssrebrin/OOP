package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Test.
 */
public class SnakeControllerTest {

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


    @Test
    void testWeirdPrimeCountEmptyMatrix() {
        int[][] input = {};
        int result = SnakeController.countWeirdPrimesInMatrix(input);
        assertEquals(0, result);
    }

    @Test
    void testWeirdPrimeCountNullMatrix() {
        int result = SnakeController.countWeirdPrimesInMatrix(null);
        assertEquals(0, result);
    }

    @Test
    void testWeirdPrimeCountWithNullRows() {
        int[][] input = {
            null,
            {13, 17, 37},
            {41}
        };
        int result = SnakeController.countWeirdPrimesInMatrix(input);
        assertEquals(2, result);
    }
}