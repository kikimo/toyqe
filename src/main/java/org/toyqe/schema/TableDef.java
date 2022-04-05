package org.toyqe.schema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.toyqe.engine.SqlException;

public class TableDef {
    private String name;
    private String alias;
    private List<ColDef> colDefs;

    // TODO: handle alias and duplicate column
    private void verifyCols(List<ColDef> colDefs) throws SqlException {
        if (this.name == null) {
            return;
        }

        Set<String> keys = new HashSet<>();
        // TODO handle alias
        for (ColDef col : colDefs) {
            if (keys.contains(col.getName())) {
                throw new SqlException("duplicate column key: " + col.getName());
            }

            keys.add(col.getName());
        }
    }

    public TableDef(String name, String alias, List<ColDef> colDefs) throws SqlException {
        this.name = name;
        this.alias = alias;
        this.colDefs = colDefs;

        verifyCols(colDefs);
        if (this.name != null) {
            for (ColDef colDef : colDefs) {
                colDef.setTableDef(this);
            }
        }
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

    public TableDef cloneTable() throws SqlException {
        List<ColDef> colDefCopies = new ArrayList<>();
        for (ColDef colDef : colDefs) {
            ColDef newColDef = colDef.clone();
            colDefCopies.add(newColDef);
        }

        TableDef tableDef = new TableDef(name, alias, colDefCopies);
        return tableDef;
    }
}
