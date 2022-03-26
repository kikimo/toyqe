package org.toyqe.schema;

import java.util.List;

public class TableDef {
    private String name;
    private List<ColDef> columns;

    public TableDef(String name, List<ColDef> columns) {
        this.name = name;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColDef> getColumns() {
        return columns;
    }

    public void setColumns(List<ColDef> columns) {
        this.columns = columns;
    }

}
