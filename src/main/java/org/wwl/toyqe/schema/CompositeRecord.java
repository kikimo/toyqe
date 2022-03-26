package org.wwl.toyqe.schema;

import java.util.List;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class CompositeRecord implements Record {

	private Record leftRecord;
	private Record rightRecord;
	private TableDef tableDef;

	public CompositeRecord(Record leftRecord, Record rightRecord) {
		this.leftRecord = leftRecord;
		this.rightRecord = rightRecord;
		this.tableDef = new TableDef(this.leftRecord.getTableDef(), this.rightRecord.getTableDef());
	}

	@Override
	public TableDef getTableDef() {
		return this.tableDef;
	}

	@Override
	public PrimitiveValue getColumn(ColumnDef col) {
		PrimitiveValue val = this.leftRecord.getColumn(col);
		if (val != null) {
			return val;
		}

		return this.rightRecord.getColumn(col);
	}

	@Override
	public List<ColTuple> getColumns() {
		List<ColTuple> cols = this.leftRecord.getColumns();
		cols.addAll(this.rightRecord.getColumns());
		return cols;
	}

}
