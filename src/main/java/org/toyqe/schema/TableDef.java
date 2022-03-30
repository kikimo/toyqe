package org.toyqe.schema;

import java.util.ArrayList;
import java.util.List;

public class TableDef {
    private String name;
    private String alias;
    private List<ColDef> colDefs;

    public TableDef(String name, String alias) {
        this.name = name;
        this.alias = alias;
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

    public List<ColDef> getColDefs() {
        return colDefs;
    }

    public void setColDefs(List<ColDef> colDefs) {
        for (ColDef colDef : colDefs) {
            colDef.setTableDef(this);
        }

        this.colDefs = colDefs;
    }

    public TableDef clone() {
        TableDef tableDef = new TableDef(name, alias);
        List<ColDef> colDefCopies = new ArrayList<>();
        for (ColDef colDef : colDefs) {
            ColDef newColDef = colDef.clone();
            colDefCopies.add(newColDef);
        }
        tableDef.setColDefs(colDefs);

        return tableDef;
    }
}
