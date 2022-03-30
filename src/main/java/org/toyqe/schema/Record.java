package org.toyqe.schema;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.toyqe.engine.SqlException;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class Record {
    private List<ColDef> colDefs;

    private Map<String, PrimitiveValue> vMap;

    public Record(List<ColDef> cols, String[] vals) throws SqlException {
        if (cols.size() != vals.length) {
            throw new SqlException("col value size mismatch: " + cols.size() + " vs " + vals.length);
        }

        this.colDefs = cols;
        this.vMap = new LinkedHashMap<>();
        for (int i = 0; i < cols.size(); i++) {
            ColDef colDef = cols.get(i);
            String valStr = vals[i];
            String key = colDef.colKey();
            PrimitiveValue value = ColDataTypeUtils.toPrimitiveValue(colDef.getColDataType(), valStr);
            if (vMap.containsKey(key)) {
                throw new SqlException("duplicate key: " + key);
            }

            vMap.put(key, value);
        }
    }

    public Record(List<ColDef> cols, byte[][] vals) {
        throw new UnsupportedOperationException();
    }

    public PrimitiveValue getColVal(ColDef colDef) {
        String key = colDef.colKey();
        return vMap.get(key);
    }

    public List<ColTuple> getColVals() {
        List<ColTuple> vals = new ArrayList<>();

        for (ColDef colDef : colDefs) {
            String key = colDef.colKey();
            PrimitiveValue val = vMap.get(key);
            ColTuple tuple = new ColTuple(colDef, val);
            vals.add(tuple);
        }

        return vals;
    }

    public Record merge(Record other) throws SqlException {
        colDefs.addAll(other.colDefs);
        for (Entry<String, PrimitiveValue> entry: other.vMap.entrySet()) {
            if (vMap.containsKey(entry.getKey())) {
                throw new SqlException("duplicate key: " + entry.getKey());
            }

            vMap.put(entry.getKey(), entry.getValue());
        }

        return this;
    }

    public Record clone() {
        throw new UnsupportedOperationException();
    }
}
