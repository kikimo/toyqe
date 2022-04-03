package org.toyqe.iterator;

import org.toyqe.engine.SqlException;
import org.toyqe.schema.Record;

public interface RecordIterator {
    boolean hasNext() throws SqlException;
    Record next() throws SqlException;
    void reset() throws SqlException;
    RecordIterator cloneIterator() throws SqlException;
    void close() throws SqlException;
}
