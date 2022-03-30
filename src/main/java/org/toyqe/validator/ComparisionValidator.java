package org.toyqe.validator;

import org.toyqe.engine.ExecutionScope;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.PrimitiveType;

public class ComparisionValidator implements Validator {
    private BinaryExpression binaryExpression;

    private ExecutionScope scope;

    public ComparisionValidator(Expression expression, ExecutionScope scope) {
        this.binaryExpression = (BinaryExpression) expression;
        this.scope = scope;
    }

    private boolean isNumber(PrimitiveType pType) {
        return pType == PrimitiveType.DOUBLE || pType == PrimitiveType.LONG;
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        Expression left = binaryExpression.getLeftExpression();
        Expression right = binaryExpression.getRightExpression();
        PrimitiveType leftType = new ExpressionValidator(left, scope).validate();
        PrimitiveType rightType = new ExpressionValidator(right, scope).validate();

        if (leftType == PrimitiveType.STRING && rightType == PrimitiveType.STRING) {
            return PrimitiveType.BOOL;
        }

        if (isNumber(leftType) && isNumber(rightType)) {
            return PrimitiveType.BOOL;
        }

        throw new ValidateException("not a valid comparision expression: " + binaryExpression);
    }
}
