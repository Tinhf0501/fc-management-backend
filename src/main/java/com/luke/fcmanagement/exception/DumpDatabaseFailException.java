package com.luke.fcmanagement.exception;

public class DumpDatabaseFailException extends RuntimeException {
    public DumpDatabaseFailException(String msg) {
        super(msg);
    }
}
