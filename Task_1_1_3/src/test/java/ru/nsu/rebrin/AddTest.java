package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
public class AddTest {

    @Test
    public void testPrint() {
        Expression left = new Number(2);
        Expression right = new Variable("x");
        Add add = new Add(left, right);

        String expected = "(2+x)";
        assertEquals(expected, add.print());
    }

    @Test
    public void testDerivative() {
        Expression left = new Number(2);
        Expression right = new Variable("x");
        Add add = new Add(left, right);

        Expression expected = new Add(new Number(0), new Number(1));
        assertEquals(expected.print(), add.derivative("x").print());
    }

    @Test
    public void testSteval() {
        Expression left = new Number(2);
        Expression right = new Variable("x");
        Add add = new Add(left, right);

        Map<String, Integer> variables = new HashMap<>();
        variables.put("x", 3);

        int expected = 5;
        assertEquals(expected, add.steval(variables));
    }

    @Test
    public void test1() {
        Expression e = new Expr().parser("1+1");
        assertEquals("2", e.simis().print());
    }

    @Test
    public void test2() {
        Expression e = new Expr().parser("0+a");
        assertEquals("a", e.simis().print());
    }

    @Test
    public void test3() {
        Expression e = new Expr().parser("a+0");
        assertEquals("a", e.simis().print());
    }

    @Test
    public void test4() {
        Expression e = new Expr().parser("a+b");
        assertEquals("a+b", e.simis().print());
    }
}