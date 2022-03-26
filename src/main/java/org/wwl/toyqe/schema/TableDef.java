package org.wwl.toyqe.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class TableDef {
	private String tableName;
	private HashMap<String, ColumnDef> colMap;
	
	public TableDef(TableDef ...tableDefs) {
		List<String> tableNames = new ArrayList<>();
		this.colMap = new LinkedHashMap<>();
		for (TableDef tableDef : tableDefs) {
			tableNames.add(tableDef.getTableName());
			
			List<ColumnDef> cols = tableDef.getColumns();
			for (ColumnDef col : cols) {
				this.colMap.put(col.getColName(), col);
			}
		}

		this.tableName = String.join("x", tableNames);
	}

	public TableDef(String tableName, List<ColumnDef> cols) {
		this.tableName = tableName;
		this.colMap = new LinkedHashMap<>();
		for (ColumnDef col : cols) {
			this.colMap.put(col.getColName(), col);
		}
	}

	public String getTableName() {
		return this.tableName;
	}

	public List<ColumnDef> getColumns() {
		return new ArrayList<>(this.colMap.values());
	}

	public ColumnDef getColumn(String colName) {
		if (this.colMap.containsKey(colName)) {
			return this.colMap.get(colName);
		}

		// TODO: use custom exception
		throw new NullPointerException();
	}
}
