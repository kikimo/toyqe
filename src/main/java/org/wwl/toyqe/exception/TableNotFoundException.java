package org.wwl.toyqe.exception;

public class TableNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2306186151444126751L;

	public TableNotFoundException(String schema) {
		super(schema);
	}
}
