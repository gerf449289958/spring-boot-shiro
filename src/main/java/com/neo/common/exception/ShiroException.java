package com.neo.common.exception;

public class ShiroException extends RuntimeException {

    public ShiroException() {
        super();
    }

    public ShiroException(String message) {
        super(message);
    }

    public ShiroException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShiroException(Throwable cause) {
        super(cause);
    }

    protected ShiroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
