package com.jryyy.forum.exception;

import com.jryyy.forum.constant.GlobalStatus;

public class GlobalException extends Exception {
    GlobalStatus status;

    public GlobalException(GlobalStatus status) {
        this.status = status;
    }
}
