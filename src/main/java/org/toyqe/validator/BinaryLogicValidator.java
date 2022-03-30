package org.toyqe.validator;

import org.toyqe.engine.ExecutionScope;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.PrimitiveType;

public class BinaryLogicValidator implements Validator {
    private BinaryExpression binaryExpression;

    private ExecutionScope scope;

    public BinaryLogicValidator(Expression expression, ExecutionScope scope) {
        binaryExpression = (BinaryExpression) expression;
        this.scope = scope;
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        Expression left = binaryExpression.getLeftExpression();
        Expression right = binaryExpression.getRightExpression();

        PrimitiveType leftType = new ExpressionValidator(left, scope).validate();
        if (leftType != PrimitiveType.BOOL) {
            throw new ValidateException("illegal logic expression: " + left);
        }

        PrimitiveType rightType = new ExpressionValidator(right, scope).validate();
        if (rightType != PrimitiveType.BOOL) {
            throw new ValidateException("illegal logic expression: " + right);
        }

        return PrimitiveType.BOOL;
    }
    
}
