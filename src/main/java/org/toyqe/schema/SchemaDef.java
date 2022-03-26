package org.toyqe.schema;

import java.util.LinkedHashMap;
import java.util.Map;

import org.toyqe.engine.SqlException;

public class SchemaDef {
    private String name;
    private Map<String, TableDef> tables;

    public SchemaDef(String name) {
        this.name = name;
        this.tables = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTable(TableDef table) throws SqlException {
        if (table == null) {
            throw new SqlException("cannot add null table");
        }

        if (this.tables.containsKey(table.getName())) {
            throw new SqlException("duplicate table: " + table.getName());
        }

        this.tables.put(table.getName(), table);
    }

    public TableDef getTable(String name) throws SqlException {
        if (!this.tables.containsKey(name)) {
            throw new SqlException("table not found: " + name);
        }

        return this.tables.get(name);
    }
}
