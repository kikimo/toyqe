package org.toyqe.validator;

import org.toyqe.engine.ExecutionScope;
import org.toyqe.engine.SqlException;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.PrimitiveType;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class SelectColumnValidator implements Validator {
    private ExecutionScope scope;

    private SelectItem item;

    public SelectColumnValidator(SelectItem item, ExecutionScope scope) {
        this.scope = scope;
        this.item = item;
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        try {
            if (item instanceof SelectExpressionItem) {
                return validateSelectExpressionItem((SelectExpressionItem) item);
            }

            if (item instanceof AllColumns) {
                return validateAllColumns((AllColumns) item);
            }

            if (item instanceof AllTableColumns) {
                return validateAllTableColumns((AllTableColumns) item);
            }

            throw new UnsupportedOperationException(item.getClass().toGenericString());
        } catch (SqlException e) {
            throw new ValidateException(e);
        }
    }

    private PrimitiveType validateSelectExpressionItem(SelectExpressionItem selectExpressionItem) throws SqlException {
        Expression expression = selectExpressionItem.getExpression();
        Validator validator = new ExpressionValidator(expression, scope);

        try {
            return validator.validate();
        } catch (ValidateException e) {
            throw new SqlException("error validating select expression: " + selectExpressionItem.getExpression(), e);
        }
    }

    private PrimitiveType validateAllColumns(AllColumns allColumns) throws SqlException {
        throw new SqlException("not implemented");
    }

    private PrimitiveType validateAllTableColumns(AllTableColumns allTableColumns) throws SqlException {
        throw new SqlException("not implemented");
    }
}
