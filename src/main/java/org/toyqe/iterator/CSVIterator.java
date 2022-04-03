package org.toyqe.iterator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.toyqe.engine.SqlException;
import org.toyqe.schema.ColDef;
import org.toyqe.schema.Record;
import org.toyqe.schema.TableDef;
import org.wwl.toyqe.utils.PrimitiveValueUtils;

import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.statement.create.table.ColDataType;

public class CSVIterator implements RecordIterator {

    private String csvFilePath;
    
    private TableDef tableDef;

    private Iterator<CSVRecord> it;

    private Reader reader;

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

        CSVRecord csvRecord = null;
        try {
            csvRecord = it.next();
        // } catch (IOException e) {
        } catch (Exception e) {
            throw new SqlException(e);
        }

        if (csvRecord.size() != colDefs.size()) {
            throw new SqlException("illegal csv record column size: " + colDefs.size() + ", expected: " + colDefs.size());
        }
        for (int i = 0; i < vals.length; i++) {
            vals[i] = csvRecord.get(i);
        }

        if (vals.length != colDefs.size()) {
            throw new SqlException("illegal csv record size: " + vals.length + ", expected: " + colDefs.size());
        }

        Map<String, PrimitiveValue> vMap = new LinkedHashMap<>();
        for (int i = 0; i < vals.length; i++) {
            ColDef colDef = colDefs.get(i);
            String vStr = vals[i];
            ColDataType colDataType = colDef.getColDataType();
            String key = colDef.colKey();
            PrimitiveValue val = PrimitiveValueUtils.fromString(vStr, colDataType.getDataType());
            vMap.put(key, val);
        }
        Record record = new Record(vMap);

        return record;
    }

    @Override
    public void reset() throws SqlException {
        File f = new File(csvFilePath);

		try {
            close();
            reader = new BufferedReader(new FileReader(f.getAbsolutePath()));
            it = CSVFormat.newFormat('|').parse(reader).iterator();
        } catch (IOException e) {
            throw new SqlException(e);
        }
    }

    @Override
    public RecordIterator cloneIterator() throws SqlException {
        RecordIterator copy = new CSVIterator(tableDef, csvFilePath);
        return copy;
    }

    @Override
    public void close() throws SqlException {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            throw new SqlException(e);
        }
    }
}
