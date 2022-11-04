package com.dss.exception;

public class DuplicateMovieException extends RuntimeException {
    public DuplicateMovieException() {
        super();
    }

    public DuplicateMovieException(String message) {
        super(message.concat("Duplicate movie id."));
    }

    public DuplicateMovieException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateMovieException(Throwable cause) {
        super(cause);
    }

    public DuplicateMovieException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
