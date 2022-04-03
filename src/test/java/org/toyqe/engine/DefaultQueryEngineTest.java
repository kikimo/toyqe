package org.toyqe.engine;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.toyqe.meta.InMemoryMetaStore;
import org.toyqe.meta.MetaStore;

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
                engine.execute(c.stmt);
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
            // new SelectTestCase("SELECT * FROM R", null),
            // new SelectTestCase("SELECT R.* FROM R, S", null),  // select all table column
            new SelectTestCase("SELECT R.A, S.C FROM R, S", null),
            new SelectTestCase("SELECT R.A, a_r.b as k, S.C FROM R as a_r, S", null),
            // new SelectTestCase("SELECT R.A, S.D FROM R, S", new SqlException()),
            // new SelectTestCase("SELECT R.A, S.C FROM R, S, T", new SqlException()),
        };
        doTest(engine, cases);
    }
}
