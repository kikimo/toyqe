package org.wwl.toyqe.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.StringReader;
import java.security.PrivilegedAction;

import org.junit.Test;
import org.wwl.toyqe.playground.SimpleStatementVisitor;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.schema.PrimitiveType;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

public class ExpressionValidatorTest {

    private Expression expressionFromString(String exprStr) throws ParseException {
        String sqlExpr = "select " + exprStr;
		StringReader sr = new StringReader(sqlExpr);
		CCJSqlParser parser = new CCJSqlParser(sr);
		
		// tem.out.print("$> ");
		Statement statement = parser.Statement();
        Select select = (Select) statement;
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        SelectExpressionItem expressionItem = (SelectExpressionItem) plainSelect.getSelectItems().get(0);
        Expression expression = expressionItem.getExpression();

        return expression;
    }

    private class ExpressionCase {
        private String expr;
        private Exception throwException;
        private PrimitiveType expectType;

        public ExpressionCase(String expr, Exception throwException, PrimitiveType expectType) {
            this.expr = expr;
            this.throwException = throwException;
            this.expectType = expectType;
        }
    }

    private void testExpressionCases(ExpressionCase[] cases) {
        for (int i = 0; i < cases.length; i++) {
            ExpressionCase c = cases[i];

            try {
                Expression expr = expressionFromString(c.expr);
                ExpressionValidator validator = new ExpressionValidator(expr);
                PrimitiveType pType = validator.validate();
                if (c.throwException != null) {
                    fail("expect exception: " + c.throwException + " but found nothing.");
                }

                assertEquals(c.expectType, pType);
            } catch (Exception e) {
                if (c.throwException != null) {
                    if (e.getClass() != c.throwException.getClass()) {
                        fail("unexpected exception:");
                        e.printStackTrace();
                    }
                } else {
                    e.printStackTrace();
                    fail("validate failed:");
                }
            }
        }

    }

    @Test
    public void testLogicExpression() {
        ExpressionCase[] cases = {
            new ExpressionCase("1 > 2 and 2 > 3", null, PrimitiveType.BOOL),
            new ExpressionCase("1 > 2 or 2 > 3", null, PrimitiveType.BOOL),
            new ExpressionCase("not 1 > 2", null, PrimitiveType.BOOL),
            new ExpressionCase("not (1 > 2 or 2 < 3)", null, PrimitiveType.BOOL),
        };

        testExpressionCases(cases);
    }


    @Test
    public void testNumberComparisonExpression() {
        ExpressionCase[] cases = {
            new ExpressionCase("1 > 1", null, PrimitiveType.BOOL),
            new ExpressionCase("1 >= 2", null, PrimitiveType.BOOL),
            new ExpressionCase("1 < 2", null, PrimitiveType.BOOL),
            new ExpressionCase("1 <= 2", null, PrimitiveType.BOOL),
            new ExpressionCase("1 < 2 < 3", null, PrimitiveType.BOOL),
            new ExpressionCase("'efg' < 'abc'", null, PrimitiveType.BOOL),
            new ExpressionCase("'efg' < 1", new ValidateException(), PrimitiveType.BOOL),
            new ExpressionCase("1 < (2 + 3)", null, PrimitiveType.BOOL),
        };

        testExpressionCases(cases);
    }

    @Test
    public void testValidateArithmeticExpression() {
        ExpressionCase[] cases = {
            new ExpressionCase("1 + 2", null, PrimitiveType.LONG),
            new ExpressionCase("1 - 2", null, PrimitiveType.LONG),
            new ExpressionCase("1 * 2", null, PrimitiveType.LONG),
            new ExpressionCase("1 / 2", null, PrimitiveType.LONG),
            new ExpressionCase("1 / 2.0", null, PrimitiveType.DOUBLE),
            new ExpressionCase("(1 + 2) * (2 / 3) - (2 * 4 )", null, PrimitiveType.LONG),
        };

        testExpressionCases(cases);
    }
}
