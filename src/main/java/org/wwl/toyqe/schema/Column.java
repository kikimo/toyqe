package org.wwl.toyqe.schema;

public class Column {
	private String name;
	private String type;
	private int index;

	public Column(String name, String type, int index) {
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
