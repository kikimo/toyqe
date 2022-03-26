package org.wwl.toyqe.schema;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class ColTuple {
	private ColumnDef columnDef;
	private PrimitiveValue value;

	public ColumnDef getColumnDef() {
		return columnDef;
	}
	public PrimitiveValue getValue() {
		return value;
	}
	
	public ColTuple(ColumnDef columnDef, PrimitiveValue value) {
		this.columnDef = columnDef;
		this.value = value;
	}
}
