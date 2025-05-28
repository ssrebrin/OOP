package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
public class MulTest {

    @Test
    public void testPrint() {
        Expression left = new Number(2);
        Expression right = new Variable("x");
        Mul mul = new Mul(left, right);

        String expected = "(2*x)";
        assertEquals(expected, mul.print());
    }

    @Test
    public void testDerivative() {
        Expression left = new Number(2);
        Expression right = new Variable("x");
        Mul mul = new Mul(left, right);

        Expression expected = new Add(new Mul(new Number(0), right), new Mul(left, new Number(1)));
        assertEquals(expected.print(), mul.derivative("x").print());
    }

    @Test
    public void testSteval() {
        Expression left = new Number(2);
        Expression right = new Variable("x");
        Mul mul = new Mul(left, right);

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 3);

        int expected = 6; // 2 * 3 = 6
        assertEquals(expected, mul.steval(variables));
    }

    @Test
    public void test1() {
        Expression e = new Mul(new Number(1), new Variable("x"));
        assertEquals("x", e.simis().print());
    }

    @Test
    public void test2() {
        Expression e = new Mul(new Number(0), new Variable("x"));
        assertEquals("0", e.simis().print());
    }

    @Test
    public void test3() {
        Expression e = new Mul(new Variable("x"), new Number(1));
        assertEquals("x", e.simis().print());
    }

    @Test
    public void test4() {
        Expression e = new Mul(new Variable("x"), new Variable("y"));
        assertEquals("(x*y)", e.simis().print());
    }

    @Test
    public void test5() {
        Expression e = new Mul(new Number(2), new Number(3));
        assertEquals("6", e.simis().print());
    }

    @Test
    public void test6() {
        Expression e = new Mul(new Variable("x"), new Number(1));
        assertEquals("x", e.simis().print());
    }

    @Test
    public void test7() {
        Expression e = new Mul(new Variable("x"), new Number(0));
        assertEquals("0", e.simis().print());
    }
}
