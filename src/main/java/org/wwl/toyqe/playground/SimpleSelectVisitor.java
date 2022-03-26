package org.wwl.toyqe.playground;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.Union;

public class SimpleSelectVisitor implements SelectVisitor {

	@Override
	public void visit(PlainSelect plainSelect) {
		System.out.println("visit plain select: " + plainSelect);
		System.out.println("from item: " + plainSelect.getFromItem());
		List<SelectItem> selectItems = plainSelect.getSelectItems();
		System.out.println("select item: ");
		for (SelectItem sItem : selectItems) {
			sItem.accept(new SimpleSelectItemVisitor());
		}

		List<Join> joins = plainSelect.getJoins();
		System.out.println("joins: ");
		if (joins == null) {
			joins = new ArrayList<>();
		}
		for (Join join : joins) {
			Expression on = join.getOnExpression();
			System.out.println("join on expression: " + on);

			FromItem fromItem = join.getRightItem();
			System.out.println("join from item: " + fromItem);

			List<Column> columns = join.getUsingColumns();
			if (columns == null) {
				columns = new ArrayList<>();
			}
			System.out.println("join using columns:");
			for (Column column : columns) {
				System.out.println("column: " + column);
			}
			// System.out.println(join);
		}

		Expression where = plainSelect.getWhere();
		System.out.println("where expression: " + where);
	}

	@Override
	public void visit(Union union) {
		System.out.println("visit union: " + union);
	}

}
