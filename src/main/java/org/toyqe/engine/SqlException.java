package org.toyqe.engine;

public class SqlException extends Exception {

    public SqlException() {
    }

    public SqlException(String arg0) {
        super(arg0);
    }

    public SqlException(Throwable arg0) {
        super(arg0);
    }

    public SqlException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public SqlException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }
    
}
