package org.wwl.toyqe.schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.statement.create.table.ColumnDefinition;


public class Schema {
	private String name;
	private Map<String, Column> cols;
	
	public Schema(String schema, List<ColumnDefinition> colList) {
		this.name = schema;
		this.cols = new HashMap<String, Column>();
		
		for (int i = 0; i < colList.size(); i++) {
			String colName = colList.get(i).getColumnName();
			String colType = colList.get(i).getColDataType().getDataType();
			Column col = new Column(colName, colType, i);
			
			this.cols.put(colName, col);
		}
	}

	public String getName() {
		return name;
	}


	public Column getColumn(String colName) {
		return this.cols.get(colName);
	}
}
