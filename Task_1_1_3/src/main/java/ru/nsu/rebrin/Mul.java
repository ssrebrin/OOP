package ru.nsu.rebrin;

import java.util.Map;

class Mul extends Expression {
    private Expression left;
    private Expression right;

    /**
     * Mul.
     *
     * @param left - left
     * @param right - right
     */
    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
        this.clas = 3;
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

    @Override
    public Expression simis() {
        int flag = 0;
        try {
            return new Number(this.eval(""));
        } catch (IllegalArgumentException w) {
            try {
                left = left.simis();
                int a1 = left.eval("");
                if (a1 == 1) {
                    flag = 1;
                    left = new Number(1);
                }
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
                if (a2 == 0) {
                    return new Number(0);
                }
                if (flag == 1)
                    return right;
            } catch (IllegalArgumentException e2) {
                right = right.simis();
                if(flag == 1){
                    return right;
                }
            }
            return new Mul(left, right);
        }
    }
}
