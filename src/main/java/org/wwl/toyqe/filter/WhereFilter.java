package org.wwl.toyqe.filter;

import org.wwl.toyqe.iterator.CondFilterIterator;
import org.wwl.toyqe.iterator.RAIterator;

import net.sf.jsqlparser.expression.Expression;

public class WhereFilter implements Filter {
	private Expression expr;

	public WhereFilter(Expression expr) {
		this.expr = expr;
	}

	@Override
	public RAIterator filter(RAIterator it) {
		return new CondFilterIterator(it, expr);
	}
}
