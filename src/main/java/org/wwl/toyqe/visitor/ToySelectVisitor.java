package org.wwl.toyqe.visitor;

import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.Union;

public class ToySelectVisitor implements SelectVisitor {

	private FromItemVisitor fromItemVisitor;
	
	public ToySelectVisitor() {
		fromItemVisitor = new ToyFromItemVisitor();
	}

	public void visit(PlainSelect plainSelect) {
		plainSelect.getFromItem().accept(fromItemVisitor);
	}

	public void visit(Union union) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(union.toString());
	}

}
