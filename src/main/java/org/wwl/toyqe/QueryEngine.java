package org.wwl.toyqe;

public interface QueryEngine {
    ResultSet execute(String stmt) throws SqlException;
}
