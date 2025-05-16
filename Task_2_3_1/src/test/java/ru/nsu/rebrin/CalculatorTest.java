package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void testAddition() {
        assertEquals(5, Calculator.add(2, 3));
    }

    @Test
    void testSubtraction() {
        assertEquals(1, Calculator.subtract(4, 3));
    }

    @Test
    void testMultiplication() {
        assertEquals(12, Calculator.multiply(3, 4));
    }

    @Test
    void testDivision() {
        assertEquals(2, Calculator.divide(10, 5));
    }

    @Test
    void testDivisionByZeroThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Calculator.divide(5, 0));
    }
}
