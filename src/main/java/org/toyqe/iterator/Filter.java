package org.toyqe.iterator;

import org.toyqe.engine.SqlException;
import org.toyqe.schema.Record;

public interface Filter {
    boolean filter(Record record) throws SqlException;
}
