package ru.nsu.rebrin;

import java.util.Map;


class Div extends Expression {
    private Expression left;
    private Expression right;

    /**
     * Div.
     *
     * @param left  - left
     * @param right - right
     */
    public Div(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String print() {
        return "(" + left.print() + "/" + right.print() + ")";
    }

    public Expression derivative(String variable) {
        return new Div(
                new Sub(new Mul(left.derivative(variable), right),
                        new Mul(left, right.derivative(variable))),
                new Mul(right, right));
    }

    @Override
    public int steval(Map<String, Integer> variables) {
        int a = left.steval(variables);
        int b = right.steval(variables);
        return a / b;
    }

    @Override
    public Expression simis() {
        try {
            return new Number(this.eval(""));
        } catch (IllegalArgumentException w) {
            try {
                int a = this.eval("");
                return new Number(a);
            } catch (IllegalArgumentException e) {
                try {
                    left = left.simis();
                    int a1 = left.eval("");
                    if (a1 == 0) {
                        return new Number(0);
                    }
                } catch (IllegalArgumentException e1) {
                    left = left.simis();
                }

                try {
                    right = right.simis();
                    int a2 = right.eval("");
                    if (a2 == 1) {
                        return left;
                    }
                } catch (IllegalArgumentException e2) {
                    right = right.simis();
                }
                return new Div(left, right);
            }
        }
    }
}
