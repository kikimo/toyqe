package org.wwl.toyqe.iterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.management.loading.PrivateClassLoader;

import org.apache.commons.csv.CSVRecord;
import org.wwl.toyqe.exception.DuplicateTableException;
import org.wwl.toyqe.exception.TableNotFoundException;
import org.wwl.toyqe.schema.ToyTable;
import org.wwl.toyqe.schema.ToyColumn;
import org.wwl.toyqe.utils.PrimitiveValueUtils;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class CSVBindingRecord implements BindingRecord {

	/**
	 * A CSV record with table info
	 * @author wenlinwu
	 *
	 */
	private class Record {
		public Record(ToyTable table, CSVRecord record) {
			this.table = table;
			this.record = record;
		}

		private ToyTable table;
		private CSVRecord record;
	}

	private Map<String, Record> recordMap; // map table to record

	public CSVBindingRecord() {
		this.recordMap = new HashMap<>();
	}
	
	public void addRecord(ToyTable table, CSVRecord record) {
		String tableName = table.getName();
		Record r = new Record(table, record);
		if (this.recordMap.containsKey(tableName)) {
			throw new DuplicateTableException(tableName);
		}
		
		this.recordMap.put(tableName, r);
	}

	private PrimitiveValue doGetColumn(Record r, String colName) {
		ToyColumn col = r.table.getColumn(colName);
		
		if (col == null) {
			return null;
		}

		int index = col.getIndex();
		String colType = col.getType();
		String val = r.record.get(index);

		return PrimitiveValueUtils.fromString(val, colType);
	}

	@Override
	public PrimitiveValue getColumn(String tableName, String colName) {
		if (tableName != null) {
			if (!this.recordMap.containsKey(tableName)) {
				System.out.println("table name: " + tableName);
				System.out.println("keys:" + this.recordMap.keySet().toString());
				throw new TableNotFoundException(tableName);
			}
			
			
			Record r = this.recordMap.get(tableName);
			return this.doGetColumn(r, colName);
		}

		for (Entry<String, Record> e : this.recordMap.entrySet()) {
			Record r = e.getValue();
			PrimitiveValue pv = this.doGetColumn(r, colName);
			if (pv != null) {
				return pv;
			}
		}

		throw new IllegalArgumentException("column " + colName + " not found");
	}

	@Override
	public List<PrimitiveValue> getColumnList() {
		List<PrimitiveValue> vals = new ArrayList<>();
		for (Entry<String, Record> e : recordMap.entrySet()) {
			String tableName = e.getKey();
			List<String> cols = e.getValue().table.getCols();
			for (String colName : cols) {
				vals.add(this.getColumn(tableName, colName));
			}
		}

		return vals;
	}

}
