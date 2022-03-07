package org.wwl.toyqe.iterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wwl.toyqe.exception.DuplicateColumnException;
import org.wwl.toyqe.exception.TableNotFoundException;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class SimpleBindingRecord implements BindingRecord {
	List<PrimitiveValue> cols;
	Map<String, Map<String, PrimitiveValue>> colMap;
	
	public SimpleBindingRecord() {
		cols = new ArrayList<>();
		colMap = new HashMap<>();
	}

	@Override
	public PrimitiveValue getColumn(String tableName, String colName) {
		if (tableName != null && tableName != "") {
			Map<String, PrimitiveValue> m = colMap.get(tableName);
			if (m == null) {
				throw new TableNotFoundException(tableName);
			}
			
			return m.get(colName);
		}
		
		for (Map<String, PrimitiveValue> m : colMap.values()) {
			if (m.containsKey(colName)) {
				return m.get(colName);
			}
		}
		
		// warning, col not found
		return null;
	}

	@Override
	public List<PrimitiveValue> getColumnList() {
		return cols;
	}

	@Override
	public void addColumn(String tableName, String colName, PrimitiveValue pv) {
		if (tableName == null) {
			tableName = "";
		}

		if (!colMap.containsKey(tableName)) {
			colMap.put(tableName, new HashMap<>());
		}

		Map<String, PrimitiveValue> m = colMap.get(tableName);
		if (m.containsKey(colName)) {
			throw new DuplicateColumnException(tableName + "." + colName);
		}

		m.put(colName, pv);
		cols.add(pv);
	}

}
