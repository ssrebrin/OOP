package ru.nsu.rebrin;

import java.util.Map;


class Add extends Expression {
    private Expression left;
    private Expression right;

    /**
     * Add.
     *
     * @param left  - left
     * @param right - right
     */
    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String print() {
        return "(" + left.print() + "+" + right.print() + ")";
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(left.derivative(variable), right.derivative(variable));
    }

    @Override
    public Double steval(Map<String, Double> variables) {
        return left.steval(variables) + right.steval(variables);
    }

    @Override
    public Expression simis() {
        int flag = 0;
        try {
            return new Number(this.eval(""));
        } catch (IllegalArgumentException w) {
            try {
                left = left.simis();
                int a1 = left.eval("");
                if (a1 == 0) {
                    flag = 1;
                }
            } catch (IllegalArgumentException e1) {
                left = left.simis();
            }

            try {
                right = right.simis();
                int a2 = right.eval("");
                if (a2 == 0) {
                    return left;
                }
            } catch (IllegalArgumentException e2) {
                right = right.simis();
                if (flag == 1) {
                    return right;
                }
            }
            return new Add(left, right);
        }
    }
}
