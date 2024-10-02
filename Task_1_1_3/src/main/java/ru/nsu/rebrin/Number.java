package ru.nsu.rebrin;

import java.util.Map;

class Number extends Expression {
    private int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public String print() {
        return String.valueOf(value);
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public int steval(Map<String, Integer> variables) {
        return value;

    }
}