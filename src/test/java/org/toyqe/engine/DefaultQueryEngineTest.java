package org.toyqe.engine;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.toyqe.meta.InMemoryMetaStore;
import org.toyqe.meta.MetaStore;
import org.toyqe.schema.Record;

import net.sf.jsqlparser.expression.PrimitiveValue;

public class DefaultQueryEngineTest {
    private QueryEngine getTestEngine() throws SqlException {
        MetaStore metaStore = new InMemoryMetaStore();
        QueryEngine engine = new DefaultQueryEngine(metaStore);

        return engine;
    }

    @Test
    public void testCreateTable() throws SqlException {
        QueryEngine engine = getTestEngine();
        ResultSet rs = engine.execute("CREATE TABLE R (A int, B int)");
        System.out.println(rs.getSummary());
    }

    private class SelectTestCase {
        public String stmt;
        public Exception exception;

        public SelectTestCase(String stmt, Exception exception) {
            this.stmt = stmt;
            this.exception = exception;
        }
    }

    private void doTest(QueryEngine engine, SelectTestCase[] cases) {
        for (SelectTestCase c : cases) {
            try {
                ResultSet rs = engine.execute(c.stmt);
                System.out.printf("size: %d\n", rs.getRows().size());
                for (Record record : rs.getRows()) {
                    for (PrimitiveValue val : record.getColVals()) {
                        System.out.print(val);
                        System.out.print(", ");
                    }

                    System.out.println();
                }
                if (c.exception != null) {
                    fail("expect exception " + c.exception.getClass() + " but nothing found.");
                }
            } catch (SqlException e) {
                if (c.exception == null || c.exception.getClass() != e.getClass()) {
                    e.printStackTrace();
                    fail("unexpected exception: " + e);
                }
            }
        }
    }

    @Test
    public void testSimpleSelect() throws SqlException {
        QueryEngine engine = getTestEngine();
        engine.execute("CREATE TABLE R (A int, B int)");
        engine.execute("CREATE TABLE S (B int, C int)");

        // String query = "select A, B from R";
        // rs = engine.execute(query);
        SelectTestCase[] cases = {
            new SelectTestCase("SELECT A FROM R", null),
            new SelectTestCase("SELECT A, B FROM R", null),
            new SelectTestCase("SELECT B, A FROM R", null),
            // new SelectTestCase("SELECT A, A FROM R", null),
            new SelectTestCase("SELECT A + 1, A + 2 FROM R", null),
            new SelectTestCase("SELECT A FROM R where a > 0", null),
            new SelectTestCase("SELECT A FROM R where a > 0", null),
            new SelectTestCase("SELECT C FROM S", null),
            new SelectTestCase("SELECT A, R.B, C FROM R, S where R.B == S.B", null),

            // new SelectTestCase("SELECT * FROM R", null),
            // new SelectTestCase("SELECT R.* FROM R, S", null),  // select all table column
            // new SelectTestCase("SELECT R.A, S.C FROM R, S", null),
            // new SelectTestCase("SELECT R.A, a_r.b as k, S.C FROM R as a_r, S", null),
            // new SelectTestCase("SELECT R.A, S.D FROM R, S", new SqlException()),
            // new SelectTestCase("SELECT R.A, S.C FROM R, S, T", new SqlException()),
        };
        doTest(engine, cases);
    }
}
