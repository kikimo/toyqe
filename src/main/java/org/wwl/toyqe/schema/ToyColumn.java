package org.wwl.toyqe.schema;

public class ToyColumn {
	public static final String STRING = "string";
	public static final String VARCHAR = "varchar";
	public static final String CHAR = "char";
	public static final String INT = "int";
	public static final String DECIMAL = "decimal";
	public static final String DATE = "date";

//	string	StringValue
//	varchar	StringValue
//	char	StringValue
//	int	LongValue
//	decimal	DoubleValue
//	date	DateValue

	private String name;
	private String type;
	private int index;

	public ToyColumn(String name, String type, int index) {
		this.name = name;
		this.type = type;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getIndex() {
		return index;
	}
}
