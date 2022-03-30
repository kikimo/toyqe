package org.toyqe.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.toyqe.schema.ColDef;
import org.toyqe.schema.TableDef;

import net.sf.jsqlparser.schema.Column;

public class ExecutionScope {
    private Map<String, List<ColDef>> colMap;
    private Map<String, ColDef> wholeColMap;
    private Map<String, TableDef> tableMap;

    public ExecutionScope() {
        this.colMap = new HashMap<>();
        this.wholeColMap = new HashMap<>();
        this.tableMap = new HashMap<>();
    }

    public void addTable(TableDef tableDef) throws SqlException {
        addTable(tableDef, null);
    }

    public void addTable(TableDef tableDef, String alias) throws SqlException {
        String tableName = tableDef.getName().toLowerCase();
        if (tableMap.containsKey(tableName)) {
            throw new SqlException("duplicate table: " + tableDef.getName());
        }
        tableMap.put(tableDef.getName(), tableDef);

        if (alias != null) {
            alias = alias.trim().toLowerCase();
        }
        if (alias != null && tableMap.containsKey(alias)) {
            throw new SqlException("duplicate table alias: " + alias);
        }
        tableMap.put(alias, tableDef);

        List<ColDef> colDefs = tableDef.getColumns();
        for (ColDef colDef : colDefs) {
            String colName = colDef.getName().trim().toLowerCase();
            if (!colMap.containsKey(colName)) {
                colMap.put(colName, new ArrayList<>());
            }
            colMap.get(colName).add(colDef);

            String wholeColName = tableDef.getName().trim().toLowerCase() + "." + colName;
            if (this.wholeColMap.containsKey(wholeColName)) {
                throw new SqlException("duplicate whole column name: " + wholeColName);
            }
            this.wholeColMap.put(wholeColName, colDef);

            if (alias == null) {
                continue;
            }

            wholeColName = alias + "." + wholeColName;
            if (this.wholeColMap.containsKey(wholeColName)) {
                throw new SqlException("duplicate whole column name: " + wholeColName);
            }
            this.wholeColMap.put(wholeColName, colDef);
        }
    }

    public ColDef getColumn(String colName) throws SqlException {
        if (colName == null) {
            throw new SqlException("column name cannot be null: " + colName);
        }
        colName = colName.trim().toLowerCase();

        if (colMap.containsKey(colName)) {
            List<ColDef> cols = colMap.get(colName);
            if (cols.size() > 1) {
                throw new SqlException("ambiguous col name: " + colName);
            }

            return cols.get(0);
        }

        if (wholeColMap.containsKey(colName)) {
            return wholeColMap.get(colName);
        }

        for (String key : wholeColMap.keySet()) {
            System.out.println("key: " + key);
        }

        throw new SqlException("col not found: " + colName);
    }

    public ColDef getColumn(String tableName, String colName) throws SqlException {
        if (tableName == null) {
            return getColumn(colName);
        }

        tableName = tableName.trim().toLowerCase();
        String wholeColName = tableName + "." + colName;
        if (!wholeColMap.containsKey(wholeColName)) {
            throw new SqlException("column " + wholeColName + " not found.");
        }

        return wholeColMap.get(wholeColName);
    }
}
