package com.jryyy.forum.exception;

import com.jryyy.forum.constant.status.Status;

public class GlobalException extends Exception {
    public Status status;

    public GlobalException(Status status) {
        this.status = status;
    }
}
