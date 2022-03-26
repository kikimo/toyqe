package org.wwl.toyqe.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wwl.toyqe.utils.PrimitiveValueUtils;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class CSVRecord implements Record {
	private TableDef tableDef;
	private org.apache.commons.csv.CSVRecord record;
	private Map<String, Integer> columnIndexMap;
	
	public CSVRecord(TableDef tableDef, org.apache.commons.csv.CSVRecord record) {
		if (record.size() != tableDef.getColumns().size()) {
			// TODO: use custom exception
			throw new IllegalArgumentException("");
		}

		this.tableDef = tableDef;
		this.record = record;
		this.columnIndexMap = new HashMap<>();
		for (int i = 0; i < tableDef.getColumns().size(); i++) {
			ColDef col = tableDef.getColumns().get(i);
			this.columnIndexMap.put(col.getColName(), i);
		}
	}

	@Override
	public PrimitiveValue getColumn(ColDef col) {
		int i = this.columnIndexMap.get(col.getColName());
		if (i >= this.record.size()) {
			throw new IllegalArgumentException();
		}

		String vstr = this.record.get(i);
		return PrimitiveValueUtils.fromString(vstr, col.getColType());
	}

	@Override
	public List<ColTuple> getColumns() {
		List<ColTuple> ret = new ArrayList<>();
		for (int i = 0; i < this.record.size(); i++) {
			ColDef col = this.tableDef.getColumns().get(i);
			String vstr = this.record.get(i);
			PrimitiveValue val = PrimitiveValueUtils.fromString(vstr, col.getColType());
			ColTuple tuple = new ColTuple(col, val);
			ret.add(tuple);
		}

		return ret;
	}

	@Override
	public TableDef getTableDef() {
		return this.tableDef;
	}
}
