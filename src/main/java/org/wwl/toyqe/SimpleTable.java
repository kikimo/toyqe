package org.wwl.toyqe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.wwl.toyqe.filter.DumbRecordFilgter;
import org.wwl.toyqe.filter.ExpressionFilter;
import org.wwl.toyqe.filter.RecordFilter;
import org.wwl.toyqe.schema.ColDef;
import org.wwl.toyqe.schema.Record;
import org.wwl.toyqe.schema.TableDef;

import net.sf.jsqlparser.expression.Expression;

public class SimpleTable implements Table {

	private TableDef tableDef;
	private List<RecordFilter> filterChain;
	
	public SimpleTable(TableDef tableDef) {
		this(tableDef, null);
	}
	
	public SimpleTable(TableDef tableDef, List<RecordFilter> filterChain) {
		if (filterChain == null) {
			filterChain = new ArrayList<>();
			filterChain.add(new DumbRecordFilgter());
		}

		this.tableDef = tableDef;
		this.filterChain = filterChain;
	}
	
	
	@Override
	public TableDef getTableDef() {
		return this.tableDef;
	}

	@Override
	public Iterator<Record> newIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table filter(Expression e) {
		List<RecordFilter> filterChain = new ArrayList<>(this.filterChain);
		RecordFilter filter = new ExpressionFilter(e);
		filterChain.add(filter);
		SimpleTable table = new SimpleTable(this.tableDef, filterChain);

		return table;
	}

	@Override
	public Table project(List<ColDef> cols) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table join(Table table, Expression e) {
		// TODO Auto-generated method stub
		return null;
	}
}
