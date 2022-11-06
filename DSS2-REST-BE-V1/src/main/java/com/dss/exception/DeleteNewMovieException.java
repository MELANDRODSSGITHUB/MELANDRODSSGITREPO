package com.dss.exception;

public class DeleteNewMovieException extends RuntimeException{

    public DeleteNewMovieException() {
        super();
    }

    public DeleteNewMovieException(String message) {
        super(message.concat("Movies older than a year can only be deleted"));
    }

    public DeleteNewMovieException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteNewMovieException(Throwable cause) {
        super(cause);
    }

    public DeleteNewMovieException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
