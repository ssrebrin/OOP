package ru.nsu.rebrin;

import java.util.HashMap;
import java.util.Map;

/**
 * Expression class.
 */
public abstract class Expression {
    /**
     * Print.
     *
     * @return - string expression
     */
    abstract String print();

    /**
     * Derivative.
     *
     * @param variable - var
     * @return - differentiated expression
     */
    abstract Expression derivative(String variable);

    /**
     * Eval.
     *
     * @param assignments - Map of variables
     * @return - result
     */
    abstract Double steval(Map<String, Double> assignments);

    /**
     * Simplification of expression.
     *
     * @return - result
     */
    abstract Expression simis();

    /**
     * Parsing of variables for eval.
     *
     * @param assignments - input string
     * @return - map
     * @throws NumberFormatException    - Format exception
     * @throws IllegalArgumentException - Argument exception
     */
    public Map<String, Double> parseAssignments(String assignments)
            throws NumberFormatException, IllegalArgumentException {
        Map<String, Double> variables = new HashMap<>();
        String[] pairs = assignments.split(";");
        for (String pair : pairs) {
            if (pair.isEmpty()) {
                continue;
            }
            int len = 0;
            String[] parts = pair.trim().split("=");

            for (String part : parts) {
                if (!part.isEmpty()) {
                    len++;
                }
            }

            if (len != 2) {
                throw new IllegalArgumentException("Неверный формат присваивания: " + pair);
            }
            int i = 0;
            String name = parts[0].trim();
            for (String part : parts) {
                i++;
                if (!part.isEmpty()) {
                    name = part;
                    break;
                }
            }
            if (name.isEmpty()) {
                throw new IllegalArgumentException("Имя переменной не может быть пустым: " + pair);
            }

            for (int ii = i; ii < parts.length; ii++) {
                if (!parts[ii].isEmpty()) {
                    Double value = Double.parseDouble(parts[ii].trim());
                    name = name.replace(" ", "");
                    variables.put(name, value);
                    break;
                }
            }
        }
        return variables;
    }

    /**
     * Start eval.
     *
     * @param assignments - args
     * @return - number
     */
    public int eval(String assignments) {
        Double result = (Double) steval(parseAssignments(assignments));
        return result.intValue();
    }
}