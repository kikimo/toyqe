package org.wwl.toyqe.validator;

import org.toyqe.engine.ExecutionScope;
import org.toyqe.engine.SqlException;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class SelectColumnValidator {
    private ExecutionScope scope;

    public SelectColumnValidator(ExecutionScope scope) {
        this.scope = scope;
    }

    public void validate(SelectItem item) throws SqlException {
        if (item instanceof SelectExpressionItem) {
            validateSelectExpressionItem((SelectExpressionItem) item);
            return;
        }

        if (item instanceof AllColumns) {
            validateAllColumns((AllColumns) item);
            return;
        }

        if (item instanceof AllTableColumns) {
            validateAllTableColumns((AllTableColumns) item);
            return;
        }

        throw new UnsupportedOperationException(item.getClass().toGenericString());
    }

    private void validateSelectExpressionItem(SelectExpressionItem selectExpressionItem) throws SqlException {
        Expression expression = selectExpressionItem.getExpression();
        Validator validator = new ExpressionValidator(expression, scope);

        try {
            validator.validate();
        } catch (ValidateException e) {
            throw new SqlException("error validating select expression: " + selectExpressionItem.getExpression(), e);
        }
    }

    private void validateAllColumns(AllColumns allColumns) throws SqlException {
        throw new SqlException("not implemented");
    }

    private void validateAllTableColumns(AllTableColumns allTableColumns) throws SqlException {
        throw new SqlException("not implemented");
    }
}
