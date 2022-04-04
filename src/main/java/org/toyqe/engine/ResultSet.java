package org.toyqe.engine;

import java.util.ArrayList;
import java.util.List;

import org.toyqe.schema.Record;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class ResultSet {
    private List<Record> rows;

    private String summary;

    public ResultSet(String summary) {
        this.summary = summary;
    }

    public ResultSet(List<Record> rows, String summary) {
        if (rows == null) {
            rows = new ArrayList<>();
        }
        this.rows = rows;

        this.summary = summary;
    }

    List<Record> getRows() {
        return rows;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
