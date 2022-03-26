package org.wwl.toyqe.schema;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class ColTuple {
	private ColDef columnDef;
	private PrimitiveValue value;

	public ColDef getColumnDef() {
		return columnDef;
	}
	public PrimitiveValue getValue() {
		return value;
	}
	
	public ColTuple(ColDef columnDef, PrimitiveValue value) {
		this.columnDef = columnDef;
		this.value = value;
	}
}
