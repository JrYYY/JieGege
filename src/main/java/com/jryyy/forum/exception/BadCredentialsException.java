package com.jryyy.forum.exception;


public class BadCredentialsException extends Exception {
    private String message;

    public BadCredentialsException(String message) {
        super(message);
        this.message = message;
    }

    public BadCredentialsException() {
        super();
    }

    public String getMessage() {
        return this.message;
    }
}
