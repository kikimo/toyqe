package org.wwl.toyqe.exception;

public class SchemaExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6194896432953822255L;
	
	public SchemaExistException(String schemaName) {
		super(schemaName);
	}
}
