package org.wwl.toyqe.schema;

import java.util.List;

import net.sf.jsqlparser.expression.PrimitiveValue;

public interface Record {
	TableDef getTableDef();
	PrimitiveValue getColumn(ColDef col);
	List<ColTuple> getColumns();
}
