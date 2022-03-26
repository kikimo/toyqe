package org.wwl.toyqe.validator;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.PrimitiveType;

public class BinaryLogicValidator implements Validator {
    private BinaryExpression binaryExpression;

    public BinaryLogicValidator(Expression expression) {
        binaryExpression = (BinaryExpression) expression;
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        Expression left = binaryExpression.getLeftExpression();
        Expression right = binaryExpression.getRightExpression();

        PrimitiveType leftType = new ExpressionValidator(left).validate();
        if (leftType != PrimitiveType.BOOL) {
            throw new ValidateException("illegal logic expression: " + left);
        }

        PrimitiveType rightType = new ExpressionValidator(right).validate();
        if (rightType != PrimitiveType.BOOL) {
            throw new ValidateException("illegal logic expression: " + right);
        }

        return PrimitiveType.BOOL;
    }
    
}
