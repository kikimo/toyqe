package org.wwl.toyqe.validator;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.PrimitiveType;

public class ComparisionValidator implements Validator {
    private BinaryExpression binaryExpression;

    public ComparisionValidator(Expression expression) {
        this.binaryExpression = (BinaryExpression) expression;
    }

    private boolean isNumber(PrimitiveType pType) {
        return pType == PrimitiveType.DOUBLE || pType == PrimitiveType.LONG;
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        Expression left = binaryExpression.getLeftExpression();
        Expression right = binaryExpression.getRightExpression();
        PrimitiveType leftType = new ExpressionValidator(left).validate();
        PrimitiveType rightType = new ExpressionValidator(right).validate();

        if (leftType == PrimitiveType.STRING && rightType == PrimitiveType.STRING) {
            return PrimitiveType.BOOL;
        }

        if (isNumber(leftType) && isNumber(rightType)) {
            return PrimitiveType.BOOL;
        }

        throw new ValidateException("not a valid comparision expression: " + binaryExpression);
    }
}
