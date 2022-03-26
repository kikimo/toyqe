package org.wwl.toyqe;

import java.util.Iterator;
import java.util.List;

import org.wwl.toyqe.schema.ColumnDef;
import org.wwl.toyqe.schema.Record;
import org.wwl.toyqe.schema.TableDef;

import net.sf.jsqlparser.expression.Expression;

public interface Table extends Cloneable {
	TableDef getTableDef();
	Iterator<Record> newIterator();
	Table filter(Expression e);
	Table project(List<ColumnDef> cols);
	Table join(Table table, Expression e);
}
