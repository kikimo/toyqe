package org.toyqe.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.toyqe.engine.SqlException;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class Record {
    private Map<String, PrimitiveValue> vMap;

    public Record(Map<String, PrimitiveValue> vMap) throws SqlException {
        this.vMap = vMap;
    }

    public PrimitiveValue getColVal(String key) {
        return vMap.get(key);
    }

    public List<PrimitiveValue> getColVals() {
        List<PrimitiveValue> colVals = new ArrayList<>(vMap.values());

        return colVals;
    }

    public Record merge(Record other) throws SqlException {
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
