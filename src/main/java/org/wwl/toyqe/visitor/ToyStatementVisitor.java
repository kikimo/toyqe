package org.wwl.toyqe.visitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;

public class ToyStatementVisitor implements StatementVisitor {
	private SelectVisitor selectVisitor;
	private Map<String, List<ColumnDefinition>> schemas;
	
	public ToyStatementVisitor() {
		selectVisitor = new ToySelectVisitor();
		schemas = new HashMap<String, List<ColumnDefinition>>();
	}

	public void visit(Select select) {
		select.getSelectBody().accept(selectVisitor);
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
		Table table = createTable.getTable();
		String tableName = table.getName();
		List<ColumnDefinition> colDefs = createTable.getColumnDefinitions();
		this.schemas.put(tableName, colDefs);

		System.out.println("creating table " + tableName + ", schema name: " + table.getSchemaName());
		System.out.println("column defs:");
		for (ColumnDefinition cdef : colDefs) {
			cdef.getColDataType();
			System.out.println("column name: " + cdef.getColumnName() + 
					", data type: " + cdef.getColDataType() + 
					", spec string: " + cdef.getColumnSpecStrings());
		}
	}
}
