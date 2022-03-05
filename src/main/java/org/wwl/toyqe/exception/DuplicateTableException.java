package org.wwl.toyqe.exception;

public class DuplicateTableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6194896432953822255L;
	
	public DuplicateTableException(String schemaName) {
		super(schemaName);
	}
}
