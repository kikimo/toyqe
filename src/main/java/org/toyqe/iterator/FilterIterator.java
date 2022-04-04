package org.toyqe.iterator;

import org.toyqe.engine.SqlException;
import org.toyqe.schema.Record;

public class FilterIterator implements RecordIterator {

    private RecordIterator it;

    private Filter filter;

    private Record curr;

    public FilterIterator(RecordIterator it, Filter filter) {
        this.it = it;
        this.filter = filter;
    }

    @Override
    public boolean hasNext() throws SqlException {
        if (curr != null) {
            return true;
        }

        while (it.hasNext()) {
            Record tmp = it.next();
            if (filter.filter(tmp)) {
                curr = tmp;
                return true;
            }
        }

        return false;
    }

    @Override
    public Record next() throws SqlException {
        Record ret = null;

        if (curr != null) {
            ret = curr;
            curr = null;
            return ret;
        }

        while (it.hasNext()) {
            ret = it.next();
            if (filter.filter(ret)) {
                return ret;
            }
        }

        throw new SqlException("record itrator eof");
    }

    @Override
    public void reset() throws SqlException {
        it.reset();
    }

    @Override
    public RecordIterator cloneIterator() throws SqlException {
        RecordIterator itCopy = it.cloneIterator();
        return new FilterIterator(itCopy, filter);
    }

    @Override
    public void close() throws SqlException {
        it.close();
    }
}
