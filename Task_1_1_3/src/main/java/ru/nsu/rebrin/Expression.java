package ru.nsu.rebrin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public abstract class Expression {
    int ratio = 1;
    HashSet<Map<String, Integer>> vars = new HashSet<>();
    int clas;


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
    abstract int steval(Map<String, Integer> assignments);

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
     * @throws NumberFormatException - Format exception
     * @throws IllegalArgumentException - Argument exception
     */
    public Map<String, Integer> parseAssignments(String assignments)
            throws NumberFormatException, IllegalArgumentException {
        Map<String, Integer> variables = new HashMap<>();
        String[] pairs = assignments.split(";");
        for (String pair : pairs) {
            if (pair.isEmpty()){
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
            int i=0;
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

            try {

                for (int ii = i;ii<parts.length;ii++) {
                    if (!parts[ii].isEmpty()) {
                        int value = Integer.parseInt(parts[ii].trim());
                        name = name.replace(" ", "");
                        variables.put(name, value);
                        break;
                    }
                }

            } catch (NumberFormatException e) {
                throw new NumberFormatException("Неверный формат значения: " + parts[1]);
            }
        }
        return variables;
    }

    /**
     * Start eval
     *
     * @param assignments - args
     * @return - number
     */
    public int eval(String assignments) {
        return steval(parseAssignments(assignments));
    }
}