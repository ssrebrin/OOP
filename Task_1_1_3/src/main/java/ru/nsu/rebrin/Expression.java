package ru.nsu.rebrin;

import java.util.HashMap;
import java.util.Map;

abstract class Expression {
    abstract String print();

    abstract Expression derivative(String variable);

    abstract int steval(Map<String, Integer> assignments);

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

                for (int ii = i;ii<pairs.length;ii++) {
                    if (!pairs[ii].isEmpty()) {
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

    public int eval(String assignments) {
        return steval(parseAssignments(assignments));
    }
}