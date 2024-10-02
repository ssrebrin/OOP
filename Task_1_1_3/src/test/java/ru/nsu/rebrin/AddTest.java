package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

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
}