package org.wwl.toyqe;

import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.wwl.toyqe.filter.WhereFilter;
import org.wwl.toyqe.iterator.BindingRecord;
import org.wwl.toyqe.iterator.CSVTableIterator;
import org.wwl.toyqe.iterator.RAIterator;
import org.wwl.toyqe.iterator.SimpleBindingRecord;
import org.wwl.toyqe.schema.ToyTable;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import net.sf.jsqlparser.statement.select.Union;

public class ToyQueryEngine implements QueryEngineX {

	private MetaStore metaStore;

	public ToyQueryEngine() {
		this.metaStore = new MetaStore();
	}

	public void start() {
		// TODO Auto-generated method stub

	}

	public void stop() {
		// TODO Auto-generated method stub

	}
	
	public String execute(String query) throws Exception {
		query += ";"; // make sure query terminate
		StringReader sr = new StringReader(query);
		CCJSqlParser parser = new CCJSqlParser(sr);
		Statement statement = parser.Statement();

		return this.handleStatement(statement);
	}

	public String handleStatement(Statement statement) throws Exception {
		if (statement instanceof CreateTable) {
			CreateTable createTable = (CreateTable) statement; 
			return this.handleCreateTable(createTable);
		}
		
		if (statement instanceof Select) {
			Select select = (Select) statement;
			return this.handleSelect(select);
		}
		
		throw new UnsupportedOperationException(statement.getClass().getName());
	}

	public String handleCreateTable(CreateTable createTable) {
		Table table = createTable.getTable();
		String tableName = table.getName();
		// TODO: use log
		System.out.println("creating table " + tableName + ", schema name: " + table.getSchemaName());
		List<ColumnDefinition> colDefs = createTable.getColumnDefinitions();
		this.metaStore.createTable(tableName, colDefs);

		// TODO: delete me later
		System.out.println("column defs:");
		for (ColumnDefinition cdef : colDefs) {
			cdef.getColDataType();
			System.out.println("column name: " + cdef.getColumnName() + 
					", data type: " + cdef.getColDataType() + 
					", spec string: " + cdef.getColumnSpecStrings());
		}

		return "table " + tableName + " created.";
	}

	public String handleSelect(Select select) throws Exception {
		SelectBody selectBody = select.getSelectBody();
		
		if (selectBody instanceof PlainSelect) {
			PlainSelect plainSelect = (PlainSelect) selectBody;
			return handlePlainSelect(plainSelect);
		}

		if (selectBody instanceof Union) {
			Union union = (Union) selectBody;
			return handleUnion(union);
		}

		throw new UnsupportedOperationException(select.getClass().getName());
	}

	private class TransColumn {
		private String table;
		private String column;
		private PrimitiveValue pv;
		
		public TransColumn(String table, String column, PrimitiveValue pv) {
			this.table = table;
			this.column = column;
			this.pv = pv;
		}
	}

	private List<TransColumn> projectExpressionItrem(BindingRecord srcBr, SimpleBindingRecord dstBr, SelectItem item) {
		// TODO
		return null;
	}

	private List<TransColumn> project(BindingRecord br, SelectItem item) {
		if (item instanceof SelectExpressionItem) {
			// TODO
		}
		
		if (item instanceof AllColumns) {
			throw new UnsupportedOperationException(item.getClass().getName());
		}
		
		if (item instanceof AllTableColumns) {
			throw new UnsupportedOperationException(item.getClass().getName());
		}
		
		throw new UnsupportedOperationException(item.getClass().getName());
	}

	@Override
	public String handlePlainSelect(PlainSelect plainSelect) throws Exception {
		// TODO: handle select query
		FromItem fromItem = plainSelect.getFromItem();
		Expression condExpr = plainSelect.getWhere();
		List<SelectItem> selectItems = plainSelect.getSelectItems();

		if (!(fromItem instanceof Table)) {
			throw new UnsupportedOperationException(fromItem.getClass().getName());
		}
		
		// 1. construct iterator
		Table table = (Table) fromItem;
		String tableName = table.getName();
		String dataFileName = tableName.toLowerCase() + ".dat";
		Path dataFilePath = Paths.get("data", dataFileName);
		System.out.println("table name: " + tableName);
		System.out.println("data file:" + dataFilePath.toString());

		// 2. filter iterator
		ToyTable toyTable = metaStore.getSchema(tableName);
		RAIterator itr = new CSVTableIterator(dataFilePath, toyTable);
		if (condExpr != null) {
			itr = new WhereFilter(condExpr).filter(itr);
		}

		// 3. project
		List<SimpleBindingRecord> sbrList = new ArrayList<>();

		SelectItem selectItem;
		new SelectItemVisitor() {
			
			@Override
			public void visit(SelectExpressionItem arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void visit(AllTableColumns arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void visit(AllColumns arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		StringBuffer sb = new StringBuffer("");
		while (itr.hasNext()) {
			sb.append('|');
			BindingRecord br = itr.next();
			List<PrimitiveValue> vals = br.getColumnList();
			for (PrimitiveValue v : vals) {
				sb.append(v.toString());
				sb.append('|');
			}
			sb.append('\n');
		}
		
		return sb.toString();
		
		// 3. apply projection
		
		// 2. apply join?
		// 1. get iterable from fromItem
		// 2. filter 
		// 3. project

		
//		new FromItemVisitor() {
//			
//			@Override
//			public void visit(SubJoin subJoin) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void visit(SubSelect subSelect) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void visit(Table table) {
//				// TODO Auto-generated method stub
//				
//			}
//		};


		// TODO Auto-generated method stub
		/*
		String schemaName = table.getName();
		Schema schema = MetaStore.getInstance().getSchema(schemaName);
		if (schema == null) {
			throw new SchemaNotFoundException(schemaName);
		}

		*/
	}

	@Override
	public String handleUnion(Union union) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(union.getClass().getName());
	}

}
