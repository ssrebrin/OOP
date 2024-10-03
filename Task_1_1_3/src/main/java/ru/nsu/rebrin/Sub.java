package ru.nsu.rebrin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Sub extends Expression {
    private Expression left;
    private Expression right;

    /**
     * Sub.
     *
     * @param left - left
     * @param right - right
     */
    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        this.clas = 2;
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

    @Override
    public Expression simis() {
        try {
            return new Number(this.eval(""));
        } catch (IllegalArgumentException w) {
            left = left.simis();
            right = right.simis();
            if (left.print().equals(right.print())) {
                return new Number(0);
            }
            return new Sub(left, right);
        }
    }
}
