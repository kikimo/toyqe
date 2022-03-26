package org.wwl.toyqe.validator;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BooleanValue;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.InverseExpression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.PrimitiveType;
import net.sf.jsqlparser.statement.select.SubSelect;

public class ExpressionValidator implements Validator {
    private Expression e;

    public ExpressionValidator(Expression expression) {
        this.e = expression;
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        Validator validator = null;

//   LONG(),
  
//   DOUBLE(),
  
//   STRING(),
  
//   BOOL(),
  
//   DATE(),
  
//   TIMESTAMP(),
  
//   TIME(),;

        if (e instanceof StringValue) {
            return PrimitiveType.STRING;
        } 

        if (e instanceof BooleanValue) {
            return PrimitiveType.BOOL;
        } 

        if (e instanceof DateValue) {
            return PrimitiveType.DATE;
        } 

        if (e instanceof TimestampValue) {
            return PrimitiveType.TIMESTAMP;
        } 

        if (e instanceof TimeValue) {
            return PrimitiveType.TIME;
        } 

        if (e instanceof LongValue) {
            return PrimitiveType.LONG;
        } 

        if (e instanceof DoubleValue) {
            return PrimitiveType.DOUBLE;
        } 

        if (e instanceof Addition || e instanceof Multiplication ||
            e instanceof Subtraction || e instanceof Division) {
            validator = new ArithmeticValidator(e);
        } else if (e instanceof GreaterThan || e instanceof MinorThan ||
            e instanceof GreaterThanEquals || e instanceof MinorThanEquals) {
            validator = new ComparisionValidator(e);
        } else if (e instanceof AndExpression || e instanceof OrExpression) {
            validator = new BinaryLogicValidator(e);
        } else if (e instanceof InverseExpression) {
            validator = new UnaryLogicExpression(((InverseExpression) e).getExpression());
        }

        if (validator != null) {
            return validator.validate();
        }

        throw new UnsupportedOperationException("no validator for " + e.getClass().getName());
    }
}
