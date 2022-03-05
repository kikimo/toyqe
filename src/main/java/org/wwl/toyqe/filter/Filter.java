package org.wwl.toyqe.filter;

import org.wwl.toyqe.iterator.RAIterator;

public interface Filter {
	RAIterator filter(RAIterator it);
}
