package org.toyqe.meta;

import org.toyqe.engine.SqlException;
import org.toyqe.schema.SchemaDef;
import org.toyqe.schema.TableDef;

public interface MetaStore {
    String DEFAULT_SCHEMA = "default";

    void addSchema(String name) throws SqlException;
    SchemaDef getSchema(String name) throws SqlException;
    void addTable(String schema, TableDef table) throws SqlException;
    TableDef getTable(String schema, String table) throws SqlException;
}
