package org.wwl.toyqe.iterator;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.wwl.toyqe.filter.WhereEval;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.Expression;

public class CondFilterIterator implements RAIterator {

	private RAIterator it;
	private BindingRecord curr;
	private Expression expr;

	public CondFilterIterator(RAIterator it, Expression expr) {
		this.it = it;
		this.expr = expr;
		this.curr = null;
	}

	@Override
	public void reset() throws IOException {
		it.reset();
	}
	
	@Override
	public BindingRecord next() throws Exception {
		if (curr == null) {
			it.hasNext();
		}
		
		if (curr == null) {
			throw new NoSuchElementException();
		}

		BindingRecord ret = curr;
		curr = null;

		return ret;
	}
	
	@Override
	public boolean hasNext() throws Exception {
		if (curr != null) {
			return true;
		}

		while (it.hasNext()) {
			BindingRecord br = it.next();
			Eval eval = new WhereEval(br);
			if (!eval.eval(expr).toBool()) {
				continue;
			}
		
			curr = br;
			break;
		}
		
		
		return curr != null;
	}
}
