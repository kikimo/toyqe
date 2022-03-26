package org.wwl.toyqe.iterator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.wwl.toyqe.schema.ToyTable;

public class CSVTableIterator implements RAIterator {
	private Path dataFilePath;
	private Iterator<CSVRecord> it;
	private Reader reader;
	private ToyTable table;
	
	private void doReset() throws IOException {
		reader = new BufferedReader(new FileReader(dataFilePath.toAbsolutePath().toString()));
		it = CSVFormat.newFormat('|').parse(reader).iterator();
	}

	public CSVTableIterator(Path dataFilePath, ToyTable table) throws java.io.IOException {
		this.dataFilePath = dataFilePath;

		if (!Files.exists(dataFilePath)) {
			throw new FileNotFoundException(dataFilePath.toString());
		}

		if (!Files.isRegularFile(dataFilePath)) {
			throw new IllegalArgumentException("not a regular file: " + dataFilePath.toString());
		}

		this.table = table;
		this.doReset();
	}

	public CSVTableIterator(String dataFilePath, ToyTable table) throws java.io.IOException {
		// this(Path.of(dataFilePath), table);
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public BindingRecord next() {
		CSVRecord cr = it.next();
		CSVBindingRecord r = new CSVBindingRecord();
		r.addRecord(table, cr);

		return r;
	}

	@Override
	public void reset() throws IOException {
		doReset();
	}

}
