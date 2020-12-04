package com.jryyy.forum.utils.sql.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlInfoException extends Exception {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public SqlInfoException() {
    }

    public SqlInfoException(String message) {
        super(message);
        log.error(message, this);
    }
}
