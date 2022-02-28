package org.wwl.toyqe.visitor;

import java.util.List;

import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.Union;

public class ToySelectVisitor implements SelectVisitor {
	public ToySelectVisitor() {}

	public void visit(PlainSelect plainSelect) {
		// TODO(wwl): how to pass intermediate result around?
		List<?> selectItems = plainSelect.getSelectItems();
		FromItemVisitor fromItemVisitor = new ToyFromItemVisitor(selectItems);
		plainSelect.getFromItem().accept(fromItemVisitor);
	}

	public void visit(Union union) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(union.toString());
	}

}
