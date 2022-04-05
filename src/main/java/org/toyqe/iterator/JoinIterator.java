package org.toyqe.iterator;

import org.toyqe.engine.SqlException;
import org.toyqe.schema.Record;

public class JoinIterator implements RecordIterator {

    private RecordIterator left;

    private RecordIterator right;

    private Record leftCurr;

    private Record rightCurr;

    public JoinIterator(RecordIterator left, RecordIterator right) throws SqlException {
        this.left = left;
        this.right = right;

        if (left.hasNext()) {
            leftCurr = left.next();
        }

        if (right.hasNext()) {
            rightCurr = right.next();
        }
    }

    @Override
    public boolean hasNext() throws SqlException {
        return leftCurr != null && rightCurr != null;
    }

    private void moveNext() throws SqlException {
        if (right.hasNext()) {
            rightCurr = right.next();
            return;
        }

        // right eof
        rightCurr = null;
        if (!left.hasNext()) {
            leftCurr = null;
            return;
        }

        leftCurr = left.next();
        right.reset();
        if (right.hasNext()) {
            rightCurr = right.next();
        }
    }

    @Override
    public Record next() throws SqlException {
        if (leftCurr != null && right != null) {
            Record r = leftCurr.cloneRecord();
            r.merge(rightCurr);
            moveNext();

            return r;
        }

        throw new SqlException("eof");
    }

    @Override
    public void reset() throws SqlException {
        left.reset();
        right.reset();

        leftCurr = null;
        rightCurr = null;

        if (left.hasNext()) {
            leftCurr = left.next();
        }

        if (right.hasNext()) {
            rightCurr = right.next();
        }
    }

    @Override
    public RecordIterator cloneIterator() throws SqlException {
        RecordIterator leftCopy = left.cloneIterator();
        RecordIterator rightCopy = right.cloneIterator();

        RecordIterator itCopy = new JoinIterator(leftCopy, rightCopy);
        return itCopy;
    }

    @Override
    public void close() throws SqlException {
        this.left.close();
        this.right.close();
    }
}
