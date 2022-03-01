package org.wwl.toyqe.exception;

public class DuplicateSchemaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6194896432953822255L;
	
	public DuplicateSchemaException(String schemaName) {
		super(schemaName);
	}
}
