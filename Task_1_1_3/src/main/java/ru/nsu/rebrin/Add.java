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
        this.clas = 2;
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
    public int steval(Map<String, Integer> variables) {
        return left.steval(variables) + right.steval(variables);
    }

    @Override
    public Expression simis() {
        int flag = 0;
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
                    flag = 1;
                } catch (IllegalArgumentException e1) {
                    left = left.simis();
                }

                try {
                    right = right.simis();
                    int a2 = right.eval("");
                    if (a2 == 0) {
                        return left;
                    }
                    if (flag == 1) {
                        return right;
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
}
