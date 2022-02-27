package org.wwl.toyqe;

import java.io.InputStream;

import org.wwl.toyqe.visitor.ToyStatementVisitor;

import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.statement.Statement;

public class QueryEngine {
	public static void main(String []args) throws ParseException {
		InputStream input = System.in;
		CCJSqlParser parser = new CCJSqlParser(input);
		
		System.out.print("$> ");
		Statement statement;
		ToyStatementVisitor statementVisitor = new ToyStatementVisitor();
		while ((statement = parser.Statement()) != null) {
			statement.accept(statementVisitor);
			System.out.print("$> ");
		}
	}
}
