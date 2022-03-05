package org.wwl.toyqe.iterator;

import java.util.List;

import net.sf.jsqlparser.expression.PrimitiveValue;

public interface BindingRecord {
	PrimitiveValue getColumn(String tableName, String colName);
	List<PrimitiveValue> getColumnList();
}
