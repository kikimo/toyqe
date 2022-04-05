package org.toyqe.validator;

import org.toyqe.engine.ExecutionScope;

import net.sf.jsqlparser.expression.BooleanValue;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.InverseExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.PrimitiveType;

public class ExpressionValidator implements Validator {
    protected Expression e;

    protected ExecutionScope scope;

    public ExpressionValidator(Expression expression, ExecutionScope scope) {
        this.e = expression;
        this.scope = scope;
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        Validator validator = null;

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
            validator = new ArithmeticValidator(e, scope);
        } else if (e instanceof GreaterThan || e instanceof MinorThan ||
            e instanceof GreaterThanEquals || e instanceof MinorThanEquals) {
            validator = new ComparisionValidator(e, scope);
        } else if (e instanceof AndExpression || e instanceof OrExpression) {
            validator = new BinaryLogicValidator(e, scope);
        } else if (e instanceof InverseExpression) {
            validator = new UnaryLogicExpression(((InverseExpression) e).getExpression(), scope);
        } else if (e instanceof Column) {
            validator = new ColumnValidator((Column) e, scope);
        } else if (e instanceof EqualsTo) {
            validator = new ComparisionValidator(e, scope);
        }

        if (validator != null) {
            return validator.validate();
        }

        throw new UnsupportedOperationException("no validator for " + e.getClass().getName());
    }
}
