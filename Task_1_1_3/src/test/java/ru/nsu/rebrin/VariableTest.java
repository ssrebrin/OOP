package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
public class VariableTest {

    @Test
    public void testPrint() {
        Variable variable = new Variable("x");
        assertEquals("x", variable.print());
    }

    @Test
    public void testStevalWithExistingVariable() {
        Variable variable = new Variable("y");
        Map<String, Integer> variables = new HashMap<>();
        variables.put("y", 10);

        assertEquals(10, variable.steval(variables));
    }

    @Test
    public void testStevalWithMissingVariable() {
        Variable variable = new Variable("z");
        Map<String, Integer> variables = new HashMap<>();
        variables.put("y", 10);

        assertThrows(IllegalArgumentException.class, () -> {
            variable.steval(variables);
        });
    }

    @Test
    public void testDerivativeWithSameVariable() {
        Variable variable = new Variable("a");
        Expression derivative = variable.derivative("a");

        assertEquals("1", derivative.print());
    }

    @Test
    public void testDerivativeWithDifferentVariable() {
        Variable variable = new Variable("b");
        Expression derivative = variable.derivative("a");

        assertEquals("0", derivative.print());
    }

    @Test
    public void testSimis() {
        Variable variable = new Variable("c");
        Expression simplified = variable.simis();

        assertEquals("c", simplified.print());
    }
}
