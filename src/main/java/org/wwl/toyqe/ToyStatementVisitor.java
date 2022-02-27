package org.wwl.toyqe;

import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;

public class ToyStatementVisitor implements StatementVisitor {

	public void visit(Select select) {
		System.out.println(select);
	}

	public void visit(Delete delete) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(delete.toString());
	}

	public void visit(Update update) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(update.toString());
	}

	public void visit(Insert insert) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(insert.toString());
	}

	public void visit(Replace replace) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(replace.toString());

	}

	public void visit(Drop drop) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(drop.toString());
	}

	public void visit(Truncate truncate) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(truncate.toString());
	}

	public void visit(CreateTable createTable) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(createTable.toString());
	}

}
