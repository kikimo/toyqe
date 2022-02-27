package org.wwl.toyqe.exception;

public class SchemaNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2306186151444126751L;

	public SchemaNotFoundException(String schema) {
		super(schema);
	}
}
