package org.wwl.toyqe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wwl.toyqe.exception.DuplicateTableException;
import org.wwl.toyqe.exception.TableNotFoundException;
import org.wwl.toyqe.schema.ToyTable;

import net.sf.jsqlparser.statement.create.table.ColumnDefinition;

public class MetaStore {
	// TODO: delete me later
	private static MetaStore INSTANCE = new MetaStore();
	private Map<String, ToyTable> schemas;
	
	public MetaStore() {
		schemas = new HashMap<String, ToyTable>();
	}
	
	public static MetaStore getInstance() {
		return INSTANCE;
	}

	public ToyTable getSchema(String schema) {
		return this.schemas.get(schema);
	}

	public void createTable(String schemaName, List<ColumnDefinition> colDefs) {
		if (schemas.containsKey(schemaName)) {
			throw new DuplicateTableException(schemaName);
		};
		
		ToyTable schema = new ToyTable(schemaName, colDefs);
		schemas.put(schemaName, schema);
	}
	
	public void dropTable(String schemaName) {
		if (!schemas.containsKey(schemaName)) {
			throw new TableNotFoundException(schemaName);
		}
		
		schemas.remove(schemaName);
	}
}
