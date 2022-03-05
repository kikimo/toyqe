package org.wwl.toyqe.filter;

import java.sql.SQLException;

import org.wwl.toyqe.iterator.BindingRecord;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

public class WhereEval extends Eval {

	private BindingRecord br;

	public WhereEval(BindingRecord br) {
		this.br = br;
	}

	@Override
	public PrimitiveValue eval(Column col) throws SQLException {
		String tableName = col.getTable().getName();
		String colName = col.getColumnName();

		return br.getColumn(tableName, colName);
	}
}
