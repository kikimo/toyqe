package org.toyqe.iterator;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.toyqe.engine.ExecutionScope;
import org.toyqe.engine.SqlException;
import org.toyqe.schema.ColDef;
import org.toyqe.schema.Record;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class ProjectIterator implements RecordIterator {

    private RecordIterator it;

    private List<SelectItem> selectItems;

    private ExecutionScope scope;

    public ProjectIterator(RecordIterator it, List<SelectItem> selectItems, ExecutionScope scope) {
        this.it = it;
        this.selectItems = selectItems;
        this.scope = scope;
    }

    @Override
    public boolean hasNext() throws SqlException {
        return it.hasNext();
    }

    @Override
    public Record next() throws SqlException {
        Record originRecord = it.next();
        Map<String, PrimitiveValue> vMap = new LinkedHashMap<>();
        for (SelectItem item : selectItems) {
            if (item instanceof SelectExpressionItem) {
                SelectExpressionItem selectExpressionItem = (SelectExpressionItem) item;
                Eval eval = new Eval() {

                    @Override
                    public PrimitiveValue eval(Column column) throws SQLException {
                        try {
                            ColDef colDef = scope.getColumn(column);
                            return originRecord.getColVal(colDef.colKey());
                        } catch (SqlException e) {
                            throw new SQLException(e);
                        }
                    }
                    
                };

                try {
                    PrimitiveValue val = eval.eval(selectExpressionItem.getExpression());
                    String key = selectExpressionItem.getAlias();
                    if (key == null) {
                        key = selectExpressionItem.getExpression().toString();
                    }

                    vMap.put(key, val);
                } catch (SQLException e) {
                    throw new SqlException(e);
                }

            } else {
                throw new UnsupportedOperationException();
            }
        }

        Record projectedRecord = new Record(vMap);
        return projectedRecord;
    }

    @Override
    public void reset() throws SqlException {
        it.reset();;
    }

    @Override
    public RecordIterator cloneIterator() throws SqlException {
        RecordIterator itCopy = it.cloneIterator();

        return new ProjectIterator(itCopy, selectItems, scope);
    }

    @Override
    public void close() throws SqlException {
        it.close();
    }
    
}
