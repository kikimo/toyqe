package org.wwl.toyqe.visitor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.wwl.toyqe.MetaStore;
import org.wwl.toyqe.exception.SchemaNotFoundException;
import org.wwl.toyqe.schema.Schema;
import org.wwl.toyqe.schema.ToyColumn;

import net.sf.jsqlparser.eval.Eval;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;

public class ToyFromItemVisitor implements FromItemVisitor {

	private List<?> selectItems;

	public ToyFromItemVisitor(List<?> selectItems) {
		this.selectItems = selectItems;
	}

	private void project(final CSVRecord record, Schema schema) throws SQLException {
		for (int i = 0; i < selectItems.size(); i++) {
			SelectExpressionItem sexpr = (SelectExpressionItem) selectItems.get(i);
			Expression expr = sexpr.getExpression();
			Eval eval = new Eval() {
				
				@Override
				public PrimitiveValue eval(Column col) throws SQLException {
					String colName = col.getColumnName();
					ToyColumn toyCol = schema.getColumn(colName);
					record.get(toyCol.getIndex());
					switch (toyCol.getType()) {
					case ToyColumn.CHAR:
					case ToyColumn.VARCHAR:
						return new StringValue(record.get(toyCol.getIndex()));
					case ToyColumn.DECIMAL:
					case ToyColumn.INT:
						return new LongValue(record.get(toyCol.getIndex()));
					case ToyColumn.DATE:
						throw new UnsupportedOperationException(ToyColumn.DATE);
					default:
						throw new UnsupportedOperationException(toyCol.getType());
					}
				}
			};
			PrimitiveValue val = eval.eval(expr);
			System.out.print(val.toString());
			
			if (i < selectItems.size() - 1) {
				System.out.print(" | ");
			}
		}
	}

	public void visit(Table table) {
		// TODO: refactor me
		String schemaName = table.getName();
		Schema schema = MetaStore.getInstance().getSchema(schemaName);
		if (schema == null) {
			throw new SchemaNotFoundException(schemaName);
		}

		System.out.println("table name: " + schemaName);
		String dataFileName = schemaName.toLowerCase() + ".dat";
		Path dataFile = Paths.get("data", dataFileName);
		System.out.println("data file:" + dataFile.toString());
		if (!Files.exists(dataFile)) {
			System.out.println("table " + schemaName + " not found.");
			return;
		}

		if (!Files.isRegularFile(dataFile)) {
			System.out.println("data file of table " + schemaName + " is not a regular file: " + dataFile.toAbsolutePath().toString());
		}

		try {
			Reader reader = new BufferedReader(
					new FileReader(dataFile.toAbsolutePath().toString()));
			Iterable<CSVRecord> records = CSVFormat.newFormat('|').parse(reader);
			
			for (final CSVRecord record : records) {
				this.project(record, schema);
				// System.out.println("record size: " + record.size());
				// for (int i = 0; i < record.size(); i++) {
				// 	System.out.print(record.get(i));
				// 	if (i < record.size() - 1) {
				// 		System.out.print(", ");
				// 	}
				// }
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void visit(SubSelect subSelect) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(subSelect.toString());
	}

	public void visit(SubJoin subJoin) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(subJoin.toString());

	}

}
