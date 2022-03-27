package org.toyqe.engine;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


import org.toyqe.meta.MetaStore;
import org.toyqe.schema.ColDef;
import org.toyqe.schema.TableDef;
import org.wwl.toyqe.validator.SelectColumnValidator;
import org.wwl.toyqe.validator.ValidateException;
import org.wwl.toyqe.validator.WhereExpressionValidator;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.ParseException;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectItem;

public class DefaultQueryEngine implements QueryEngine {

    private MetaStore metaStore;

    public DefaultQueryEngine(MetaStore metaStore) {
        this.metaStore = metaStore;
    }

    private ResultSet handleCreateTable(CreateTable createTable) throws SqlException {
        // TODO: handle table index
        Table table = createTable.getTable();
        String tableName = table.getName();
        String schemaName = table.getSchemaName();
        if (schemaName == null) {
            schemaName = MetaStore.DEFAULT_SCHEMA;
        }
        System.out.println("schema name: " + schemaName);
        List<ColDef> colDefs = new ArrayList<>();
        for (ColumnDefinition col : createTable.getColumnDefinitions()) {
            String colName = col.getColumnName();
            ColDataType colDataType = col.getColDataType();
            System.out.println("data type: " + colDataType.getDataType());
            ColDef colDef = new ColDef(colName, colDataType, true);
            colDefs.add(colDef);
        }
        TableDef tableDef = new TableDef(tableName, colDefs);
        this.metaStore.addTable(schemaName, tableDef);

        return new ResultSet("table " + tableName + " created.");
    }

    private ResultSet doExecute(Statement statement) throws SqlException {
        if (statement instanceof CreateTable) {
            return handleCreateTable((CreateTable) statement);
        }

        if (statement instanceof Select) {
            return handleSelect((Select) statement);
        }

        throw new SqlException("unsupported statement: " + statement.getClass());
    }

    public ResultSet handleSelect(Select select) throws SqlException {
        // TODO handle with item
        SelectBody selectBody = select.getSelectBody();
        if (selectBody instanceof PlainSelect) {
            return handlePlainSelect((PlainSelect) selectBody);
        }

        throw new SqlException("unsupported statement: " + selectBody.getClass());
    }

    private TableDef tableFromItem(FromItem item) throws SqlException {
        if (!(item instanceof Table)) {
            throw new SqlException("unsupporte select item type: " + item.getClass());
        }

        Table table = (Table) item;
        return metaStore.getTable(table.getSchemaName(), table.getName());
    }

    public ResultSet handlePlainSelect(PlainSelect plainSelect) throws SqlException {
        ExecutionScope scope = new ExecutionScope();
        FromItem fromItem = plainSelect.getFromItem();
        TableDef tableDef = tableFromItem(fromItem);
        scope.addTable(tableDef, fromItem.getAlias());
        if (plainSelect.getJoins() != null) {
            for (Join join : plainSelect.getJoins()) {
                FromItem item = join.getRightItem();
                tableDef = tableFromItem(item);
                scope.addTable(tableDef);

                // TODO: handle on expression and using columns
                // join.getOnExpression();
                // join.getUsingColumns()
            }
        }

        List<SelectItem> selectItems = plainSelect.getSelectItems();
        SelectColumnValidator selectColumnValidator = new SelectColumnValidator(scope);
        for (SelectItem item : selectItems) {
            selectColumnValidator.validate(item);
        }

        Expression whereExpression = plainSelect.getWhere();
        WhereExpressionValidator validator = new WhereExpressionValidator(whereExpression, scope);
        try {
            validator.validate();
        } catch (ValidateException e) {
            throw new SqlException(e);
        }

        return null;
    }

    @Override
    public ResultSet execute(String stmt) throws SqlException {
		StringReader sr = new StringReader(stmt);
		CCJSqlParser parser = new CCJSqlParser(sr);
		
		Statement statement = null;
        try {
            ResultSet resultSet = null;
            while ((statement = parser.Statement()) != null) {
                resultSet = doExecute(statement);
            }

            return resultSet;
        } catch (ParseException e) {
            throw new SqlException("error executing:" + statement.toString(), e);
        }
    }
}
