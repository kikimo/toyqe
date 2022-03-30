package org.toyqe.schema;

import net.sf.jsqlparser.statement.create.table.ColDataType;

public class ColDef {
    private String name;
    private String alias;
    private TableDef tableDef;
    private ColDataType colDataType;
    private boolean nullable;

    public ColDef(String name, String alias, ColDataType colDataType) {
        this(name, alias, colDataType, true);
    }

    public ColDef clone() {
        ColDef colDef = new ColDef(name, alias, colDataType, nullable);
        colDef.setTableDef(tableDef);

        return colDef;
    }

    public ColDef(String name, String alias, ColDataType colDataType, boolean nullable) {
        this.name = name;
        this.alias = alias;
        this.colDataType = colDataType;
        this.nullable = nullable;
    }

    public String colKey() {
        String key = tableDef.getName() + "." + name;
        return key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public TableDef getTableDef() {
        return tableDef;
    }

    public void setTableDef(TableDef tableDef) {
        this.tableDef = tableDef;
    }

    public ColDataType getColDataType() {
        return colDataType;
    }

    public void setColDataType(ColDataType colDataType) {
        this.colDataType = colDataType;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
