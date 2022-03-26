package org.toyqe.schema;

import javax.lang.model.type.PrimitiveType;

import net.sf.jsqlparser.statement.create.table.ColDataType;

public class ColDef {
    // private TableDef table;
    private String name;
    private ColDataType colDataType;
    private boolean nullable;

    public ColDef(String name, ColDataType colDataType, boolean nullable) {
        // this.table = table;
        this.name = name;
        this.colDataType = colDataType;
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
