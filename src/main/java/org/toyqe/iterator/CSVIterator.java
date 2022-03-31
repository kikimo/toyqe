package org.toyqe.iterator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.toyqe.engine.SqlException;
import org.toyqe.schema.ColDef;
import org.toyqe.schema.Record;
import org.toyqe.schema.TableDef;

public class CSVIterator implements RecordIterator {

    private String csvFilePath;
    
    private TableDef tableDef;

    private Iterator<CSVRecord> it;

    public CSVIterator(TableDef tableDef, String csvFilePath) throws SqlException {
        this.tableDef = tableDef;
        File f = new File(csvFilePath);
        if (!f.exists()) {
            throw new SqlException("file not found: " + csvFilePath);
        }

        if (f.isDirectory()) {
            throw new SqlException("not a regular file: " + csvFilePath);
        }

        this.csvFilePath = csvFilePath;
        reset();
    }

    @Override
    public boolean hasNext() throws SqlException {
        return this.it.hasNext();
    }

    @Override
    public Record next() throws SqlException {
        List<ColDef> colDefs = tableDef.getColDefs();
        String[] vals = new String[colDefs.size()];

        CSVRecord csvRecord = it.next();
        if (csvRecord.size() != colDefs.size()) {
            throw new SqlException("illegal csv record column size: " + colDefs.size() + ", expected: " + colDefs.size());
        }
        for (int i = 0; i < vals.length; i++) {
            vals[i] = csvRecord.get(i);
        }

        Record record = new Record(colDefs, vals);
        return record;
    }

    @Override
    public void reset() throws SqlException {
        File f = new File(csvFilePath);

		try (Reader reader = new BufferedReader(new FileReader(f.getAbsolutePath()))) {
            it = CSVFormat.newFormat('|').parse(reader).iterator();
        } catch (IOException e) {
            throw new SqlException(e);
        }
    }

    @Override
    public RecordIterator cloneIterator() throws SqlException {
        RecordIterator copy = new CSVIterator(tableDef.clone(), csvFilePath);
        return copy;
    }
}
