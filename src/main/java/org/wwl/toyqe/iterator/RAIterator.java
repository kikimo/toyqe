package org.wwl.toyqe.iterator;

import java.io.IOException;

public interface RAIterator {
	boolean hasNext()throws Exception;
	BindingRecord next() throws Exception;
	void reset() throws IOException;
}
