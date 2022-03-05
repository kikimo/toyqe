package org.wwl.toyqe;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.Union;

public interface QueryEngine {
	public void start();
	public void stop();
	// TODO: use custom exception
	public String execute(String query) throws Exception;
	public String handleStatement(Statement statement) throws Exception;
	public String handleCreateTable(CreateTable createTable) throws Exception;
	public String handleSelect(Select select) throws Exception;
	public String handlePlainSelect(PlainSelect plainSelect) throws Exception;
	public String handleUnion(Union union) throws Exception;
}
