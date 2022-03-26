package org.wwl.toyqe.validator;

import java.math.BigInteger;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.PrimitiveType;

public class ArithmeticValidator implements Validator {
    private BinaryExpression binaryExpression;

    public ArithmeticValidator(Expression expression) {
        binaryExpression = (BinaryExpression) expression;
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        Expression left = binaryExpression.getLeftExpression();
        Expression right = binaryExpression.getRightExpression();
        PrimitiveType leftType = new ExpressionValidator(left).validate();
        PrimitiveType rightType = new ExpressionValidator(right).validate();
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
