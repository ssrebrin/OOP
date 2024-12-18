package ru.nsu.rebrin;

import java.util.Map;

class Number extends Expression {
    private int value;

    /**
     * Number.
     *
     * @param value - value
     */
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
    public Double steval(Map<String, Double> variables) {
        return (Double)(double)value;
    }

    @Override
    public Expression simis() {
        return new Number(this.value);
    }
}