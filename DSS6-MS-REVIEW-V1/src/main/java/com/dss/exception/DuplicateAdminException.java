package com.dss.exception;

public class DuplicateAdminException extends RuntimeException {
    public DuplicateAdminException() {
        super();
    }

    public DuplicateAdminException(String message) {
        super(message.concat("Duplicate admin id."));
    }

    public DuplicateAdminException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateAdminException(Throwable cause) {
        super(cause);
    }

    public DuplicateAdminException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
