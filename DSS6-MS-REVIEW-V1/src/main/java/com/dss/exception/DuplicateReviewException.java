package com.dss.exception;

public class DuplicateReviewException extends RuntimeException {
    public DuplicateReviewException() {
        super();
    }

    public DuplicateReviewException(String message) {
        super(message.concat("Duplicate review id."));
    }

    public DuplicateReviewException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateReviewException(Throwable cause) {
        super(cause);
    }

    public DuplicateReviewException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
