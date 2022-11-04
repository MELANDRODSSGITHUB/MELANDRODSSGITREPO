package com.dss.exception;

public class ActorHaveMovieDetailsException extends RuntimeException {
    public ActorHaveMovieDetailsException() {
        super();
    }

    public ActorHaveMovieDetailsException(String message) {
        super(message.concat("Actor that have movie details can't be deleted."));
    }

    public ActorHaveMovieDetailsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActorHaveMovieDetailsException(Throwable cause) {
        super(cause);
    }

    public ActorHaveMovieDetailsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
