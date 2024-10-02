package ru.nsu.rebrin;

import java.util.Map;

class Sub extends Expression {
    private Expression left;
    private Expression right;

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String print() {
        return "(" + left.print() + "-" + right.print() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public int steval(Map<String, Integer> variables) {
        return left.steval(variables) - right.steval(variables);

    }
}
