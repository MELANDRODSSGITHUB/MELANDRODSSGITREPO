package com.dss.exception;

public class EmailAlreadyBeenUsedException extends RuntimeException {
    public EmailAlreadyBeenUsedException() {
        super();
    }

    public EmailAlreadyBeenUsedException(String message) {
        super(message.concat("Email address is already in use by some other user."));
    }

    public EmailAlreadyBeenUsedException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyBeenUsedException(Throwable cause) {
        super(cause);
    }

    public EmailAlreadyBeenUsedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
