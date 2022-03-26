package org.wwl.toyqe.validator;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.PrimitiveType;

public class UnaryLogicExpression implements Validator {
    private Expression expression;

    public UnaryLogicExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        PrimitiveType pType = new ExpressionValidator(expression).validate();
        if (pType != PrimitiveType.BOOL) {
            throw new ValidateException("illegal logic expression: " + expression);
        }

        return PrimitiveType.BOOL;
    }


}
