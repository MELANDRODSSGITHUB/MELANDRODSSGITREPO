package com.dss.exception;

public class DuplicateActorException extends RuntimeException {
    public DuplicateActorException() {
        super();
    }

    public DuplicateActorException(String message) {
        super(message.concat("Duplicate actor id."));
    }

    public DuplicateActorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateActorException(Throwable cause) {
        super(cause);
    }

    public DuplicateActorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
