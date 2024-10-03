package ru.nsu.rebrin;

import java.util.Stack;

public class Expr {

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
                case ")":
                    while (!(a = operators.pop()).equals("(")) {
                        Expression A = operands.pop();
                        Expression B = operands.pop();
                        switch (a) {
                            case "+":
                                operands.push(new Add(B, A));
                                break;
                            case "-":
                                operands.push(new Sub(B, A));
                                break;
                            case "*":
                                operands.push(new Mul(B, A));
                                break;
                            case "/":
                                operands.push(new Div(B, A));
                                break;
                        }
                    }
                    break;
                case "(", "+", "-", "*", "/":
                    while (!token.equals("(") && !operators.isEmpty() && precedence(token) <= precedence(operators.peek())) {
                        String operator = operators.pop();
                        Expression A = operands.pop();
                        Expression B = operands.pop();
                        switch (operator) {
                            case "+":
                                operands.push(new Add(B, A));
                                break;
                            case "-":
                                operands.push(new Sub(B, A));
                                break;
                            case "*":
                                operands.push(new Mul(B, A));
                                break;
                            case "/":
                                operands.push(new Div(B, A));
                                break;
                        }
                    }
                    operators.push(token);
                    break;
                default:
                    if (token.matches("\\d+")) {
                        operands.push(new Number(Integer.parseInt(token)));
                    } else {
                        operands.push(new Variable(token));
                    }
                    break;
            }
        }

        while (!operators.isEmpty()) {
            String operator = operators.pop();
            Expression A = operands.pop();
            Expression B = operands.pop();
            switch (operator) {
                case "+":
                    operands.push(new Add(B, A));
                    break;
                case "-":
                    operands.push(new Sub(B, A));
                    break;
                case "*":
                    operands.push(new Mul(B, A));
                    break;
                case "/":
                    operands.push(new Div(B, A));
                    break;
            }
        }

        return operands.pop();
    }

    private static int precedence(String operator) {
        switch (operator) {
            case "*":
                return 3;
            case "/":
                return 2;
            case "+", "-":
                return 1;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        Expression e = new Div(
                new Mul(new Variable("x"), new Variable("x")),
                new Add(new Number(2), new Variable("x"))
        );

        Expr main = new Expr();

        System.out.println(e.print());
        System.out.println(e.derivative("x").print());
        System.out.println(main.parser("(1*x)*x").print());  // Удален вызов несуществующего метода simis()
    }
}
