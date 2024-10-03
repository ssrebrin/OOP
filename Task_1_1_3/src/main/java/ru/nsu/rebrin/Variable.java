package ru.nsu.rebrin;

import java.util.Map;

class Variable extends Expression {
    private String name;

    /**
     * Var.
     *
     * @param name - name
     */
    public Variable(String name) {
        this.name = name;
        this.clas = 1;
    }

    @Override
    public String print() {
        return name;
    }

    @Override
    public Expression derivative(String variable) {
        if (name.equals(variable)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public int steval(Map<String, Integer> variables) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException(
                "Переменная " + name + " не найдена в карте присваиваний");
        }
        return variables.get(name);
    }

    @Override
    public Expression simis() {
        return new Variable(name);
    }


}
