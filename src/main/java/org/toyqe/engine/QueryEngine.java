package org.toyqe.engine;

public interface QueryEngine {
    ResultSet execute(String stmt) throws SqlException;
}
