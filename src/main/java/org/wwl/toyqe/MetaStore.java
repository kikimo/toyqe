package org.wwl.toyqe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wwl.toyqe.exception.SchemaExistException;
import org.wwl.toyqe.exception.SchemaNotFoundException;

import net.sf.jsqlparser.statement.create.table.ColumnDefinition;

public class MetaStore {
	private static MetaStore INSTANCE = new MetaStore();
	private Map<String, List<ColumnDefinition>> schemas;

	
	private MetaStore() {
		schemas = new HashMap<String, List<ColumnDefinition>>();
	}

	public static void createTable(String tableName, List<ColumnDefinition> colDefs) {
		if (INSTANCE.schemas.containsKey(tableName)) {
			throw new SchemaExistException(tableName);
		};
		
		INSTANCE.schemas.put(tableName, colDefs);
	}
	
	public static void dropTable(String tableName) {
		if (!INSTANCE.schemas.containsKey(tableName)) {
			throw new SchemaNotFoundException(tableName);
		}
		
		INSTANCE.schemas.remove(tableName);
	}
}
