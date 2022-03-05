package org.wwl.toyqe.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.statement.create.table.ColumnDefinition;


public class ToyTable {
	private String name;
	private Map<String, ToyColumn> colMap;
	private List<String> cols;
	
	public ToyTable(String schema, List<ColumnDefinition> colList) {
		this.name = schema;
		this.colMap = new HashMap<String, ToyColumn>();
		this.cols = new ArrayList<>();
		
		for (int i = 0; i < colList.size(); i++) {
			String colName = colList.get(i).getColumnName();
			String colType = colList.get(i).getColDataType().getDataType();
			ToyColumn col = new ToyColumn(colName, colType, i);
			
			this.colMap.put(colName, col);
			this.cols.add(colName);
		}
	}

	public String getName() {
		return name;
	}

	public ToyColumn getColumn(String colName) {
		return this.colMap.get(colName);
	}
	
	public List<String> getCols() {
		return this.cols;
	}
}
