package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
public class ExprTest {

    @Test
    public void testSimpleAddition() {
        Expr expr = new Expr();
        Expression parsedExpr = expr.parser("5 + 3");
        assertEquals(8, parsedExpr.eval(""));
    }

    @Test
    public void testSimpleSubtraction() {
        Expr expr = new Expr();
        Expression parsedExpr = expr.parser("10 - 7");
        assertEquals(3, parsedExpr.eval(""));
    }

    @Test
    public void testPrecedenceOfOperators() {
        Expr expr = new Expr();
        Expression parsedExpr = expr.parser("2 + 3 * 4");
        assertEquals(14, parsedExpr.eval(""));
    }

    @Test
    public void testParentheses() {
        Expr expr = new Expr();
        Expression parsedExpr = expr.parser("(2 + 3) * 4");
        assertEquals(20, parsedExpr.eval(""));
    }

    @Test
    public void testDivision() {
        Expr expr = new Expr();
        Expression parsedExpr = expr.parser("8 / 4");
        assertEquals(2, parsedExpr.eval(""));
    }

    @Test
    public void testComplexExpression() {
        Expr expr = new Expr();
        Expression parsedExpr = expr.parser("3 + 4 * 2 / ( 1 - 5 )");
        assertEquals(1, parsedExpr.eval(""));
    }

    @Test
    public void testVariableExpression() {
        Expr expr = new Expr();
        Expression parsedExpr = expr.parser("x + 2");
        assertEquals(7, parsedExpr.eval("x = 5"));
    }

    @Test
    public void testDerivative() {
        Expression e = new Div(
                new Mul(new Variable("x"), new Variable("x")),
                new Add(new Number(2), new Variable("x"))
        );
        assertEquals("(((((1*x)+(x*1))*(2+x))-((x*x)*(0+1)))/((2+x)*(2+x)))",
            e.derivative("x").print());
    }
}
