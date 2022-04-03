package org.toyqe.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.toyqe.schema.ColDef;
import org.toyqe.schema.TableDef;

public class ExecutionScope {
    private Map<String, TableDef> tableMap;
    private Map<String, List<ColDef>> colMap;
    private Map<String, ColDef> normalizedColMap;

    public ExecutionScope() {
        this.tableMap = new HashMap<>();
        this.colMap = new HashMap<>();
        this.normalizedColMap = new HashMap<>();
    }

    public void addTable(TableDef tableDef) throws SqlException {
        addTable(tableDef, null);
    }

    public void addTable(TableDef tableDef, String alias) throws SqlException {
        String tableName = tableDef.getName();
        tableName = normalizeStr(tableName);
        if (tableName.equals("")) {
            throw new SqlException("table name cannot be empty");
        }

        if (tableMap.containsKey(tableName)) {
            throw new SqlException("duplicate table: " + tableDef.getName());
        }
        tableMap.put(tableDef.getName(), tableDef);

        alias = normalizeStr(alias);
        if (!alias.equals("")) {
            if (tableMap.containsKey(alias)) {
                throw new SqlException("duplicate table: " + alias);
            }

            tableMap.put(alias, tableDef);
        }

        for (ColDef colDef : tableDef.getColDefs()) {
            String colName = colDef.getName();
            colName = normalizeStr(colName);
            if (colName.equals("")) {
                throw new SqlException("column name cannot be empty");
            }

            if (!colMap.containsKey(colName)) {
                colMap.put(colName, new ArrayList<>());
            }

            List<ColDef> cols = colMap.get(colName);
            cols.add(colDef);
        }
    }

    private String normalizeStr(String val) {
        if (val == null) {
            return "";
        }

        val = val.trim().toLowerCase();
        return val;
    }

    public ColDef getColumn(String colName) throws SqlException {
        colName = normalizeStr(colName);
        if (!normalizedColMap.containsKey(colName)) {
            throw new SqlException("column " + colName + " not found.");
        }

        return normalizedColMap.get(colName);
    }

    public ColDef getColumn(String tableName, String colName) throws SqlException {
        tableName = normalizeStr(tableName);
        if (tableName.equals("")) {
            return getColumn(colName);
        }

        colName = normalizeStr(colName);
        if (colName.equals("")) {
            throw new SqlException("column name cannot be empty");
        }

        String wholeColName = tableName + "." + colName;
        if (!normalizedColMap.containsKey(wholeColName)) {
            throw new SqlException("column " + wholeColName + " not found.");
        }

        return normalizedColMap.get(wholeColName);
    }

    public void normalizeColumn(String colName) throws SqlException {
        colName = normalizeStr(colName);
        if (normalizedColMap.containsKey(colName)) {
            return;
        }

        if (!colMap.containsKey(colName)) {
            throw new SqlException("column not found: " + colName);
        }

        List<ColDef> cols = colMap.get(colName);
        if (cols.size() > 1) {
            throw new SqlException("ambiguous column name: " + colName);
        }

        this.normalizedColMap.put(colName, cols.get(0));
    }

    public void normalizeColumn(String tableName, String colName) throws SqlException {
        tableName = normalizeStr(tableName);
        if (tableName.equals("")) {
            normalizeColumn(colName);
            return;
        }

        colName = normalizeStr(colName);
        if (colName.equals("")) {
            throw new SqlException("column name cannot be empty");
        }

        String wholeColumnName = tableName + "." + colName;
        if (normalizedColMap.containsKey(wholeColumnName)) {
            return;
        }

        if (!tableMap.containsKey(tableName)) {
            throw new SqlException("table not found: " + tableName);
        }

        colName = normalizeStr(colName);
        if (colName == null || colName == "") {
            throw new SqlException("column not found");
        }
        if (!colMap.containsKey(colName)) {
            throw new SqlException("column not found: " + colName);
        }

        TableDef tableDef = tableMap.get(tableName);
        List<ColDef> colDefs = colMap.get(colName);
        for (ColDef colDef : colDefs) {
            if (colDef.getTableDef().getName().equals(tableDef.getName())) {
                normalizedColMap.put(wholeColumnName, colDef);
                return;
            }
        }

        throw new SqlException("column not found: " + tableName + "." + colName);
    }
}
