package org.toyqe.engine;

import javax.management.Query;

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
}
