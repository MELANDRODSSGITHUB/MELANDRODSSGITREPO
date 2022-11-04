package com.dss.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException() {
        super();
    }

    public MovieNotFoundException(String message) {
        super(message.concat("Movie not found."));
    }

    public MovieNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MovieNotFoundException(Throwable cause) {
        super(cause);
    }

    public MovieNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
