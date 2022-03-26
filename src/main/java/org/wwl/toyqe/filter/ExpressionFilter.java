package org.wwl.toyqe.filter;

import org.wwl.toyqe.schema.Record;

import net.sf.jsqlparser.expression.Expression;

public class ExpressionFilter implements RecordFilter {
	private Expression expression;

	public ExpressionFilter(Expression expression) {
		this.expression = expression;
	}

	@Override
	public boolean filter(Record record) {
		// TODO Auto-generated method stub
		return false;
	}

}
