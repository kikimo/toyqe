package org.wwl.toyqe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wwl.toyqe.exception.DuplicateSchemaException;
import org.wwl.toyqe.exception.SchemaNotFoundException;
import org.wwl.toyqe.schema.Schema;

import net.sf.jsqlparser.statement.create.table.ColumnDefinition;

public class MetaStore {
	private static MetaStore INSTANCE = new MetaStore();
	private Map<String, Schema> schemas;

	
	private MetaStore() {
		schemas = new HashMap<String, Schema>();
	}
	
	public static MetaStore getInstance() {
		return INSTANCE;
	}

	public Schema getSchema(String schema) {
		return this.schemas.get(schema);
	}

	public void createTable(String schemaName, List<ColumnDefinition> colDefs) {
		if (schemas.containsKey(schemaName)) {
			throw new DuplicateSchemaException(schemaName);
		};
		
		Schema schema = new Schema(schemaName, colDefs);
		schemas.put(schemaName, schema);
	}
	
	public void dropTable(String schemaName) {
		if (!schemas.containsKey(schemaName)) {
			throw new SchemaNotFoundException(schemaName);
		}
		
		schemas.remove(schemaName);
	}
}
