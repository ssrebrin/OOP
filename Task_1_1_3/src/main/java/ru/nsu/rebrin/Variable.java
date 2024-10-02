package ru.nsu.rebrin;

import java.util.Map;

class Variable extends Expression {
    private String name;

    public Variable(String name) {
        this.name = name;
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
        return variables.getOrDefault(name, 0);
    }
}
