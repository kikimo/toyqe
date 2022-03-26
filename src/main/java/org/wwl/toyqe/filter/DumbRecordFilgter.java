package org.wwl.toyqe.filter;

import org.wwl.toyqe.schema.Record;

public class DumbRecordFilgter implements RecordFilter {

	@Override
	public boolean filter(Record record) {
		return true;
	}

}
