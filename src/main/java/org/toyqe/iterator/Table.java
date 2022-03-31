package org.toyqe.iterator;

import java.util.List;

import org.toyqe.engine.SqlException;
import org.toyqe.schema.ColDef;
import org.toyqe.schema.TableDef;

import net.sf.jsqlparser.expression.Expression;

public class Table {
    private TableDef tableDef;

    private RecordIterator it;

    public Table(TableDef tableDef, RecordIterator it) {
        this.tableDef = tableDef;
        this.it = it;
    }

    public RecordIterator newIterator() throws SqlException {
        RecordIterator newIt = it.cloneIterator();

        return newIt;
    }

    public TableDef getTabelDef() {
        return tableDef.clone();
    }

    public Table filter(Expression expr) {
        throw new UnsupportedOperationException();
    }

    public Table project(List<ColDef> colDefs) {
        throw new UnsupportedOperationException();
    }

    public Table join(Table right, Expression onExpr) {
        throw new UnsupportedOperationException();
    }
}
