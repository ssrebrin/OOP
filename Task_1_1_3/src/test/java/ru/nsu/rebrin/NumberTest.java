package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test.
 */
public class NumberTest {

    @Test
    public void testPrint() {
        Number number = new Number(5);
        assertEquals("5", number.print());
    }

    @Test
    public void testSteval() {
        Number number = new Number(10);
        assertEquals(10, number.steval(null)); // Карта переменных не используется
    }

    @Test
    public void testDerivative() {
        Number number = new Number(7);
        assertEquals("0", number.derivative("x").print()); // Производная числа равна 0
    }

    @Test
    public void testSimis() {
        Number number = new Number(3);
        Expression simplified = number.simis();
        assertEquals("3", simplified.print()); // Упрощение числа возвращает его же
    }
}
