package ru.nsu.rebrin;

import java.util.Stack;

/**
 * Expr.
 */
public class Expr {

    /**
     * String parser.
     *
     * @param expr - parsing string
     * @return - Expression class
     */
    public Expression parser(String expr) {
        Stack<Expression> operands = new Stack<>();
        Stack<String> operators = new Stack<>();
        String a;
        expr = expr.replace("+", " + ");
        expr = expr.replace("-", " - ");
        expr = expr.replace("*", " * ");
        expr = expr.replace("/", " / ");
        expr = expr.replace("(", " ( ");
        expr = expr.replace(")", " ) ");
        String[] tokens = expr.split("\\s+");
        for (String token : tokens) {
            switch (token) {
                case "":
                    break;
                case "(":
                case "+":
                case "-":
                case "*":
                case "/":
                    while (!token.equals("(") && !operators.isEmpty()
                            && precedence(token) <= precedence(operators.peek())) {
                        String operator = operators.pop();
                        Expression aa = operands.pop();
                        Expression bb = operands.pop();
                        switch (operator) {
                            case "+":
                                operands.push(new Add(bb, aa));
                                break;
                            case "-":
                                operands.push(new Sub(bb, aa));
                                break;
                            case "*":
                                operands.push(new Mul(bb, aa));
                                break;
                            case "/":
                                operands.push(new Div(bb, aa));
                                break;
                            default:
                                break;
                        }
                    }
                    operators.push(token);
                    continue;
                case ")":
                    while (!(a = operators.pop()).equals("(")) {
                        Expression aa = operands.pop();
                        Expression bb = operands.pop();
                        switch (a) {
                            case "+":
                                operands.push(new Add(bb, aa));
                                break;
                            case "-":
                                operands.push(new Sub(bb, aa));
                                break;
                            case "*":
                                operands.push(new Mul(bb, aa));
                                break;
                            case "/":
                                operands.push(new Div(bb, aa));
                                break;
                            default:
                                break;
                        }
                    }
                    continue;
                default:
                    break;
            }

            if (!token.isEmpty()) {
                if (token.matches("\\d+")) {
                    operands.push(new Number(Integer.parseInt(token)));
                } else {
                    operands.push(new Variable(token));
                }
            }
        }
        while (!operators.isEmpty()) {
            String operator = operators.pop();
            Expression aa = operands.pop();
            Expression bb = operands.pop();
            switch (operator) {
                case "+":
                    operands.push(new Add(bb, aa));
                    break;
                case "-":
                    operands.push(new Sub(bb, aa));
                    break;
                case "*":
                    operands.push(new Mul(bb, aa));
                    break;
                case "/":
                    operands.push(new Div(bb, aa));
                    break;
                default:
                    break;
            }
        }
        return operands.pop();
    }

    /**
     * Operator prioritization.
     *
     * @param operator - operator
     * @return - Priority level
     */
    private static int precedence(String operator) {
        switch (operator) {
            case "*":
                return 2;
            case "/":
                return 3;
            case "+":
            case "-":
                return 1;
            default:
                return 0;
        }
    }
    /**
     * Main.
     *
     * @param args - args
     */
    public static void main(String[] args) {
        Expression e = new Div(
                new Mul(new Variable("x"), new Variable("x")),
                new Add(new Number(2), new Variable("x"))
        );
        Expr main = new Expr();
        System.out.println(e.print());
        System.out.println(e.derivative("x").print());
        System.out.println(main.parser("5/3*3").eval(""));
    }
}
