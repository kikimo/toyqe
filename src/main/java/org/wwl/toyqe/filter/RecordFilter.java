package org.wwl.toyqe.filter;

import org.wwl.toyqe.schema.Record;

public interface RecordFilter {
	boolean filter(Record record);
}
