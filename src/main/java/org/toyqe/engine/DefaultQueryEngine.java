package org.toyqe.engine;

import java.io.StringReader;
import java.security.cert.CertStoreException;
import java.util.ArrayList;
import java.util.List;

import org.toyqe.meta.MetaStore;
import org.toyqe.schema.ColDef;
import org.toyqe.schema.SchemaDef;
import org.toyqe.schema.TableDef;

import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.schema.PrimitiveType;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.Select;

public class DefaultQueryEngine implements QueryEngine {

    private MetaStore metaStore;

    public DefaultQueryEngine(MetaStore metaStore) {
        this.metaStore = metaStore;
    }

    private ResultSet handleCreateTable(CreateTable createTable) throws SqlException {
        // TODO: handle table index
        Table table = createTable.getTable();
        String tableName = table.getName();
        String schemaName = table.getSchemaName();
        if (schemaName == null) {
            schemaName = MetaStore.DEFAULT_SCHEMA;
        }
        System.out.println("schema name: " + schemaName);
        List<ColDef> colDefs = new ArrayList<>();
        for (ColumnDefinition col : createTable.getColumnDefinitions()) {
            String colName = col.getColumnName();
            ColDataType colDataType = col.getColDataType();
            System.out.println("data type: " + colDataType.getDataType());
            ColDef colDef = new ColDef(colName, colDataType, true);
            colDefs.add(colDef);
        }
        TableDef tableDef = new TableDef(tableName, colDefs);
        this.metaStore.addTable(schemaName, tableDef);

        return new ResultSet("table " + tableName + " created.");
    }

    private ResultSet doExecute(Statement statement) throws SqlException {
        if (statement instanceof CreateTable) {
            return handleCreateTable((CreateTable) statement);
        }

        if (statement instanceof Select) {
            // 
        }

        throw new SqlException("unsupported statement: " + statement.getClass());
    }

    @Override
    public ResultSet execute(String stmt) throws SqlException {
		StringReader sr = new StringReader(stmt);
		CCJSqlParser parser = new CCJSqlParser(sr);
		
		Statement statement = null;
        try {
            ResultSet resultSet = null;
            while ((statement = parser.Statement()) != null) {
                resultSet = doExecute(statement);
            }

            return resultSet;
        } catch (ParseException e) {
            throw new SqlException("error executing:" + statement.toString(), e);
        }
    }
}
