package org.wwl.toyqe.validator;

import org.toyqe.engine.ExecutionScope;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.PrimitiveType;

public class ArithmeticValidator implements Validator {
    private BinaryExpression binaryExpression;

    private ExecutionScope scope;

    public ArithmeticValidator(Expression expression, ExecutionScope scope) {
        binaryExpression = (BinaryExpression) expression;
        this.scope = scope;
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        Expression left = binaryExpression.getLeftExpression();
        Expression right = binaryExpression.getRightExpression();
        PrimitiveType leftType = new ExpressionValidator(left, scope).validate();
        PrimitiveType rightType = new ExpressionValidator(right, scope).validate();
        if (leftType != PrimitiveType.LONG && leftType != PrimitiveType.DOUBLE) {
            throw new ValidateException(left + " is not a validate number");
        }

        if (rightType != PrimitiveType.LONG && rightType != PrimitiveType.DOUBLE) {
            throw new ValidateException(right + " is not a validate number");
        }

        if (leftType == PrimitiveType.DOUBLE || rightType == PrimitiveType.DOUBLE) {
            return PrimitiveType.DOUBLE;
        }

        return PrimitiveType.LONG;
    }
}
