package com.jryyy.forum.exception;

import com.jryyy.forum.constant.GlobalStatus;

/**
 * @author OU
 */
public class GlobalException extends Exception {
    GlobalStatus status;

    public GlobalException(GlobalStatus status) {
        super(status.getMsg());
        this.status = status;
    }
}
