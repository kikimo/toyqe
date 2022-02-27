package org.wwl.toyqe;

import java.io.InputStream;

import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.statement.Statement;

public class ToyQueryEngine {
	public static void main(String []args) throws ParseException {
		InputStream input = System.in;
		CCJSqlParser parser = new CCJSqlParser(input);
		
		System.out.print("$> ");
		Statement statement;
		while ((statement = parser.Statement()) != null) {
			System.out.println(statement);
			System.out.print("$> ");
		}
	}
}
