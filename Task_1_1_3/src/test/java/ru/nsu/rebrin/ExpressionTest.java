package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
class ExpressionTest {
    private Expression parser = new Number(1);

    @Test
    public void testParseAssignments_ValidInput() {
        String assignments = "x=10;y=5;z=15";
        Map<String, Integer> expected = new HashMap<>();
        expected.put("x", 10);
        expected.put("y", 5);
        expected.put("z", 15);

        Map<String, Double> result = null;
        try {
            result = parser.parseAssignments(assignments);
        } catch (IllegalArgumentException e) {
            fail("Пупупу", e);
        }

        assertEquals(expected, result);
    }

    @Test
    public void testParseAssignments_EmptyInput() {
        String assignments = "";
        Map<String, Integer> expected = new HashMap<>();

        Map<String, Double> result = null;
        try {
            result = parser.parseAssignments(assignments);
        } catch (IllegalArgumentException e) {
            fail("Пупупу", e);
        }

        assertEquals(expected, result);
    }

    @Test
    public void testParseAssignments_InvalidFormat() {
        String assignments = "x=10;y=5;z=1=2";

        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments(assignments));
    }

    @Test
    public void testParseAssignments_InvalidValue() {
        String assignments = "x=10;y=abc;z=15";

        assertThrows(NumberFormatException.class, () -> parser.parseAssignments(assignments));
    }

    @Test
    public void testParseAssignments_EmptyName() {
        String assignments = "=10;y=5;z=15";

        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments(assignments));
    }

    @Test
    public void testParseAssignments_EmptyValue() {
        String assignments = "x=;y=5;z=15";

        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments(assignments));
    }

    @Test
    public void testParseAssignments_MultipleEquals() {
        String assignments = "x=10=15;y=5;z=15";

        assertThrows(IllegalArgumentException.class, () -> parser.parseAssignments(assignments));
    }

    @Test
    public void testParseAssignments_MultipleSpaces() {
        String assignments = "x  =  10;y=5;z=15";

        Map<String, Integer> expected = new HashMap<>();
        expected.put("x", 10);
        expected.put("y", 5);
        expected.put("z", 15);

        Map<String, Double> result = null;
        try {
            result = parser.parseAssignments(assignments);
        } catch (IllegalArgumentException e) {
            fail("Пупупу", e);
        }

        assertEquals(expected, result);
    }
}