package org.toyqe.validator;

import org.toyqe.engine.ExecutionScope;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.PrimitiveType;

public class UnaryLogicExpression implements Validator {
    private Expression expression;
    private ExecutionScope scope;

    public UnaryLogicExpression(Expression expression, ExecutionScope scope) {
        this.expression = expression;
        this.scope = scope;
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        PrimitiveType pType = new ExpressionValidator(expression, scope).validate();
        if (pType != PrimitiveType.BOOL) {
            throw new ValidateException("illegal logic expression: " + expression);
        }

        return PrimitiveType.BOOL;
    }


}
