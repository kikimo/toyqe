package org.toyqe.iterator;

import java.util.ArrayList;
import java.util.List;

import org.toyqe.engine.ExecutionScope;
import org.toyqe.engine.SqlException;
import org.toyqe.schema.ColDataTypeUtils;
import org.toyqe.schema.ColDef;
import org.toyqe.schema.TableDef;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.PrimitiveType;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class BindingTable {
    private ExecutionScope scope;

    private TableDef tableDef;

    private RecordIterator it;

    public BindingTable(TableDef tableDef, RecordIterator it, ExecutionScope scope) {
        this.tableDef = tableDef;
        this.it = it;
        this.scope = scope;
    }

    public RecordIterator newIterator() throws SqlException {
        RecordIterator newIt = it.cloneIterator();

        return newIt;
    }

    public TableDef getTabelDef() throws SqlException {
        return tableDef.cloneTable();
    }

    public BindingTable filter(Expression expr) throws SqlException {
        Filter filter = new ExpressionFilter(expr, scope);
        RecordIterator filteredIt = new FilterIterator(it.cloneIterator(), filter);
        BindingTable table = new BindingTable(tableDef, filteredIt, scope);

        return table;
    }

    public BindingTable project(List<SelectItem> selectItems) throws SqlException {
        List<ColDef> colDefs = new ArrayList<>();
        for (SelectItem item : selectItems) {
            if (item instanceof SelectExpressionItem) {
                SelectExpressionItem selectExpressionItem = (SelectExpressionItem) item;
                int hCode = selectExpressionItem.hashCode();
                PrimitiveType pType = scope.getSelectItemType(hCode);
                ColDataType colDataType = ColDataTypeUtils.toColDataType(pType);
                String name = selectExpressionItem.getAlias();
                if (name == null) {
                    name = selectExpressionItem.getExpression().toString();
                }
                ColDef colDef = new ColDef(name, null, colDataType);
                colDefs.add(colDef);
            } else {
                throw new UnsupportedOperationException();
            }
        }

        TableDef bindingTableDef = new TableDef("", "", colDefs);
        BindingTable table = new BindingTable(bindingTableDef, it, scope);
        return table;
    }

    public BindingTable join(BindingTable right, Expression onExpr) {
        throw new UnsupportedOperationException();
    }
}
