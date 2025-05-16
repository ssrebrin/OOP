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

    @Test
    void testWeirdPrimeCountSimple() {
        int[][] input = {
                {2, 3, 5},
                {7, 11, 17}, // 7 and 17 should be ignored (contain digit 7)
                {19, 23, 29}
        };
        int result = SnakeController.countWeirdPrimesInMatrix(input);
        assertEquals(6, result); // 2, 3, 5, 11, 19, 23
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
        assertEquals(2, result); // 13, 41
    }
}