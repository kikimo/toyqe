package org.wwl.toyqe;

import java.io.InputStream;
import java.io.StringReader;

import org.wwl.toyqe.visitor.ToyStatementVisitor;

import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.statement.Statement;

public class SimpleQueryEngine {
	

	public String execute(String cmd) {
		// TODO
		return "";
	}
	
	public void start() {
		
	}
	

	// what's the input
	// what's the output
	public static void main(String []args) throws ParseException {
		// from sql to logic execution plan
		// from logic execution plan to physical execution plan
		InputStream input = System.in;
		StringReader sr = new StringReader("");
		CCJSqlParser parser = new CCJSqlParser(sr);
		
		System.out.print("$> ");
		Statement statement;
		ToyStatementVisitor statementVisitor = new ToyStatementVisitor();
		while ((statement = parser.Statement()) != null) {
			statement.accept(statementVisitor);
			System.out.print("$> ");
		}
	}
}
