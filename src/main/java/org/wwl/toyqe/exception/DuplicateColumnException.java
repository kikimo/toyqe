package org.wwl.toyqe.exception;

public class DuplicateColumnException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2387907745452930157L;

	public DuplicateColumnException(String colName) {
		super(colName);
	}
}
