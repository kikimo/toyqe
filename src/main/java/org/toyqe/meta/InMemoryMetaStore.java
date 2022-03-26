package org.toyqe.meta;

import java.util.LinkedHashMap;
import java.util.Map;

import org.toyqe.engine.SqlException;
import org.toyqe.schema.SchemaDef;
import org.toyqe.schema.TableDef;

public class InMemoryMetaStore implements MetaStore {
    private Map<String, SchemaDef> schemas;

    public InMemoryMetaStore() throws SqlException {
        this.schemas = new LinkedHashMap<>();
        this.addSchema(MetaStore.DEFAULT_SCHEMA);
    }

    @Override
    public void addTable(String schema, TableDef table) throws SqlException {
        if (!this.schemas.containsKey(schema)) {
            throw new SqlException("schema not found: " + schema);
        }

        SchemaDef schemaDef = this.schemas.get(schema);
        schemaDef.addTable(table);
    }

    @Override
    public TableDef getTable(String schema, String table) throws SqlException {
        if (!this.schemas.containsKey(schema)) {
            throw new SqlException("schema not found: " + schema);
        }

        SchemaDef schemaDef = this.schemas.get(schema);
        return schemaDef.getTable(table);
    }

    @Override
    public void addSchema(String name) throws SqlException {
        if (this.schemas.containsKey(name)) {
            throw new SqlException("duplicate schema: " + name);
        }

        SchemaDef schemaDef = new SchemaDef(name);
        this.schemas.put(name, schemaDef);
    }

    @Override
    public SchemaDef getSchema(String name) throws SqlException {
        if (!this.schemas.containsKey(name)) {
            throw new SqlException("schema not found: " + name);
        }

        SchemaDef schemaDef = this.schemas.get(name);
        return schemaDef;
    }
    
}
