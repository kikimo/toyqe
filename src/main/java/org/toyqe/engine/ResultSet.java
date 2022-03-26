package org.toyqe.engine;

import java.util.ArrayList;
import java.util.List;

import org.wwl.toyqe.schema.ColDef;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class ResultSet {
    private List<List<PrimitiveValue>> rows;

    private List<ColDef> columns;

    private String summary;

    public ResultSet(String summary) {
        this(null, null);
        this.summary = summary;
    }

    public ResultSet(List<List<PrimitiveValue>> rows, List<ColDef> columns) {
        if (rows == null) {
            rows = new ArrayList<>();
        }
        this.rows = rows;

        if (columns == null) {
            columns = new ArrayList<>();
        }
        this.columns = columns;
    }

    List<List<PrimitiveValue>> getRows() {
        return rows;
    }

    List<ColDef> getColumns() {
        return columns;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
