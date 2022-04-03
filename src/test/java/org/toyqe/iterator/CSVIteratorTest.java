package org.toyqe.iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.toyqe.engine.SqlException;
import org.toyqe.schema.ColDef;
import org.toyqe.schema.Record;
import org.toyqe.schema.TableDef;
import org.wwl.toyqe.schema.ToyColumn;

import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.PrimitiveValue.InvalidPrimitive;
import net.sf.jsqlparser.statement.create.table.ColDataType;

public class CSVIteratorTest {

    private RecordIterator newIterator() throws SqlException {
        String csvFilePath = "data/datasets/Sanity_Check_Examples/data/r.dat";
        String tableName = "R";
        ColDataType typeInt = new ColDataType();
        typeInt.setDataType(ToyColumn.INT);
        List<ColDef> colDefs = Arrays.asList(
            new ColDef("A", "", typeInt),
            new ColDef("B", "", typeInt)
        );

        // create edge known2(idx string, ts datetime default datetime());
        TableDef tableDef = new TableDef(tableName, "", colDefs);
        RecordIterator it = new CSVIterator(tableDef, csvFilePath);
        return it;
    }

    @Test
    public void testCloneIterator() {
    }

    private List<long[]> toList(RecordIterator it) throws InvalidPrimitive, SqlException {
        List<long[]> rows = new ArrayList<long[]>();
        while (it.hasNext()) {
           Record record = it.next();
           assertNotNull(record);
            List<PrimitiveValue> vals = record.getColVals();
            assertEquals(2, vals.size());

            long[] row = {vals.get(0).toLong(), vals.get(1).toLong()};
            rows.add(row);
        }

        return rows;
    }

    @Test
    public void testIterator() throws SqlException, InvalidPrimitive {
        RecordIterator it = newIterator();
        assertTrue("iterater has next", it.hasNext());
        List<long[]> rows = toList(it);
        assertTrue("empty it", rows.size() > 0);

        it.reset();
        List<long[]> newRows = toList(it);
        assertEquals(rows.size(), newRows.size());

        for (int i = 0; i < rows.size(); i++) {
            long[] a = rows.get(i);
            long[] b = rows.get(i);
            assertEquals(a, b);
            System.out.println(a[0] + ", " + a[1]);
        }

        it.close();
        try {
            it.next();
            fail("expect exception here");
        } catch (SqlException e) {
        }
    }
}
