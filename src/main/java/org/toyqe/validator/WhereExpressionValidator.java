package org.toyqe.validator;

import org.toyqe.engine.ExecutionScope;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.PrimitiveType;

public class WhereExpressionValidator extends ExpressionValidator {
    public WhereExpressionValidator(Expression expression, ExecutionScope scope) {
        super(expression, scope);
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        if (this.e == null) {
            return PrimitiveType.BOOL;
        }

        PrimitiveType pType = super.validate();
        if (pType != PrimitiveType.BOOL) {
            throw new ValidateException("result of where expression should be of bool type: " + this.e);
        }

        return PrimitiveType.BOOL;
    }
    
}
