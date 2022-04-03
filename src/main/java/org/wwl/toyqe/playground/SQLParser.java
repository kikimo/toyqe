package org.wwl.toyqe.playground;

import java.io.StringReader;

import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;

public class SQLParser {
	public static void main(String []args) throws ParseException {
		// from sql to logic execution plan
		// from logic execution plan to physical execution plan
//		InputStream input = System.in;
		// String sql = "SELECT * FROM R, S WHERE (R.B = S.B);";
		// SELECT R.A, S.C FROM R, S WHERE (R.B = S.B) AND ((R.A > S.C) or (R.A < S.C));
		// SELECT R.A, T.D FROM R, S, T WHERE (R.B = S.B) AND (T.C < S.C);

//		SELECT R.A, S.C FROM R, S;
//		SELECT R.A, R.B, S.B, S.C FROM R, S;
//
//		SELECT R.A, S.C FROM R JOIN S;
//		SELECT R.A, R.B, S.B, S.C FROM R JOIN S;
//
//		SELECT R.A, S.C FROM R JOIN S ON R.B = S.B;
		// String sql = "SELECT R.A, R.B, S.B, S.C FROM R JOIN S ON R.B = S.B;";
		String sql = "select a_s.a, t from s as a_s;";
		// String sql = "select c from t as a_;";

		// String sql = "SELECT A FROM R;";
		// String sql = "SELECT * FROM R, S WHERE (R.B = S.B);";
//
//		SELECT R.A, S.C FROM R, S WHERE R.B = S.B;
//		SELECT R.A, R.B, S.B, S.C FROM R, S WHERE R.B = S.B;

		StringReader sr = new StringReader(sql);
		CCJSqlParser parser = new CCJSqlParser(sr);
		
		// tem.out.print("$> ");
		Statement statement;
		Column column = null;
		Table table = null;
		System.out.println(null + "hello world");
		StatementVisitor statementVisitor = new SimpleStatementVisitor();
		while ((statement = parser.Statement()) != null) {
			statement.accept(statementVisitor);
//			System.out.print("$> ");
		}
	}
	
}
