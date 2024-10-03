package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
public class SubTest {

    @Test
    public void testPrint() {
        Expression left = new Number(5);
        Expression right = new Number(3);
        Sub subtraction = new Sub(left, right);

        assertEquals("(5-3)", subtraction.print());
    }

    @Test
    public void testSteval() {
        Expression left = new Number(10);
        Expression right = new Number(4);
        Sub subtraction = new Sub(left, right);

        HashMap<String, Integer> variables = new HashMap<>();
        int expected = 6; // 10 - 4
        assertEquals(expected, subtraction.steval(variables));
    }

    @Test
    public void testDerivative() {
        Expression left = new Variable("x");
        Expression right = new Number(5);
        Sub subtraction = new Sub(left, right);

        // Производная: d/dx (x - 5) = 1 - 0 = 1
        assertEquals("(1-0)", subtraction.derivative("x").print());
    }

    @Test
    public void testSimisEqual() {
        Expression left = new Number(7);
        Expression right = new Number(7);
        Sub subtraction = new Sub(left, right);

        // Упрощение: 7 - 7 = 0
        assertEquals("0", subtraction.simis().print());
    }

    @Test
    public void testSimisNotEqual() {
        Expression left = new Number(10);
        Expression right = new Number(5);
        Sub subtraction = new Sub(left, right);

        // Упрощение: 10 - 5 = 5
        assertEquals("5", subtraction.simis().print());
    }
}
