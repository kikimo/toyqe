package org.toyqe.validator;

import org.toyqe.engine.ExecutionScope;
import org.toyqe.engine.SqlException;
import org.toyqe.schema.ColDef;
import org.toyqe.schema.ColDataTypeUtils;
import org.wwl.toyqe.utils.PrimitiveValueUtils;

import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.PrimitiveType;
import net.sf.jsqlparser.statement.create.table.ColDataType;

public class ColumnValidator implements Validator {
    private Column column;
    private ExecutionScope scope;

    public ColumnValidator(Column column, ExecutionScope scope) {
        this.column = column;
        this.scope = scope;
    }

    @Override
    public PrimitiveType validate() throws ValidateException {
        String wholeColumnName = column.getWholeColumnName();

        try {
            scope.normalizeColumn(column.getTable().getName(), column.getColumnName());
            ColDef colDef = scope.getColumn(column.getTable().getName(), column.getColumnName());
            ColDataType colDataType = colDef.getColDataType();

            return ColDataTypeUtils.toPrimitiveType(colDataType);
        } catch (SqlException e) {
            throw new ValidateException("column not found: " + wholeColumnName, e);
        }
    }
    
}
