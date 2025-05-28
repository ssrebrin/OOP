package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
public class DivTest {

    @Test
    public void testPrint() {
        Expression left = new Number(6);
        Expression right = new Variable("x");
        Div div = new Div(left, right);

        String expected = "(6/x)";
        assertEquals(expected, div.print());
    }

    @Test
    public void testDerivative() {
        Expression left = new Number(6);
        Expression right = new Variable("x");
        Div div = new Div(left, right);

        Expression expected = new Div(
                new Sub(new Mul(new Number(0), right), new Mul(left, new Number(1))),
                new Mul(right, right)
        );
        assertEquals(expected.print(), div.derivative("x").print());
    }

    @Test
    public void testSteval() {
        Expression left = new Number(6);
        Expression right = new Variable("x");
        Div div = new Div(left, right);

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 3);

        int expected = 2; // 6 / 3 = 2
        assertEquals(expected, div.steval(variables));
    }

    @Test
    public void testSimis1() {
        Expression e = new Div(new Number(6), new Number(2));
        assertEquals("3", e.simis().print());
    }

    @Test
    public void testSimis2() {
        Expression e = new Div(new Number(0), new Variable("x"));
        assertEquals("0", e.simis().print());
    }

    @Test
    public void testSimis3() {
        Expression e = new Div(new Variable("x"), new Number(1));
        assertEquals("x", e.simis().print());
    }

    @Test
    public void testSimis4() {
        Expression e = new Div(new Number(6), new Variable("x"));
        assertEquals("(6/x)", e.simis().print());
    }
}
