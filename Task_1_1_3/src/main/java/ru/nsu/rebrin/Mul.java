package ru.nsu.rebrin;

import java.util.Map;

class Mul extends Expression {
    private Expression left;
    private Expression right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String print() {
        return "(" + left.print() + "*" + right.print() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(
                new Mul(left.derivative(variable), right),
                new Mul(left, right.derivative(variable))
        );
    }

    @Override
    public int steval(Map<String, Integer> variables) {
        return left.steval(variables) * right.steval(variables);

    }
}
