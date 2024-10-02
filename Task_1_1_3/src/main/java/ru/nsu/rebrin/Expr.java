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
                case "" -> {
                    break;
                }
                case "(", "+", "-", "*", "/" -> {
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
                    continue;
                }
                case ")" -> {
                    while (!(a = operators.pop()).equals("(")) {
                        switch (a) {
                            case "+":
                                operands.push(new Add(operands.pop(), operands.pop()));
                                break;
                            case "-":
                                operands.push(new Sub(operands.pop(), operands.pop()));
                                break;
                            case "*":
                                operands.push(new Mul(operands.pop(), operands.pop()));
                                break;
                            case "/":
                                operands.push(new Div(operands.pop(), operands.pop()));
                                break;
                        }
                    }
                    continue;
                }
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
        return switch (operator) {
            case "*" -> 3;
            case "/" -> 2;
            case "+", "-" -> 1;
            default -> 0; // Ошибка: неизвестный оператор
        };
    }

    public static void main(String[] args) {
        Expression e = new Div(
                new Mul(new Variable("x"), new Variable("x")),
                new Add(new Number(2), new Variable("x"))
        );

        Expr main = new Expr();

        System.out.println(e.print());
        System.out.println(e.derivative("x").print());
        System.out.println(e.eval("x=8;y=0"));
        System.out.println(main.parser(" 2 -6*(x+1) /x*x*x").print());
    }
}