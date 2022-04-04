package org.toyqe.iterator;

import java.sql.SQLException;

import org.toyqe.engine.ExecutionScope;
import org.toyqe.engine.SqlException;
import org.toyqe.schema.ColDef;
import org.toyqe.schema.Record;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.schema.Column;

public class ExpressionFilter implements Filter {

    private ExecutionScope scope;

    private Expression expression;

    public ExpressionFilter(Expression expression, ExecutionScope scope) {
        this.scope = scope;
        this.expression = expression;
    }

    @Override
    public boolean filter(Record record) throws SqlException {
        Eval eval = new Eval() {

            @Override
            public PrimitiveValue eval(Column column) throws SQLException {
                try {
                    ColDef colDef = scope.getColumn(column);
                    PrimitiveValue val = record.getColVal(colDef.colKey());
                    return val;
                } catch (SqlException e) {
                    throw new SQLException(e);
                }
            }
            
        };

        try {
            PrimitiveValue ret = eval.eval(expression);
            return ret.toBool();
        } catch (SQLException e) {
            throw new SqlException(e);
        }
    }
    
}
