package org.wwl.toyqe.utils;

import org.wwl.toyqe.schema.ToyColumn;

import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.StringValue;

public class PrimitiveValueUtils {
	public static PrimitiveValue fromString(String val, String colType){
		switch (colType) {
		case ToyColumn.CHAR:
		case ToyColumn.VARCHAR:
			return new StringValue(val);

		case ToyColumn.DATE:
			throw new UnsupportedOperationException("date");

		case ToyColumn.DECIMAL:
			return new DoubleValue(val);

		case ToyColumn.INT:
			return new LongValue(val);

		default:
			throw new UnsupportedOperationException(colType);
		}
	}
}
