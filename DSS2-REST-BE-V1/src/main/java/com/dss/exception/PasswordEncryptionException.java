package com.dss.exception;

public class PasswordEncryptionException extends RuntimeException {
    public PasswordEncryptionException() {
        super();
    }

    public PasswordEncryptionException(String message) {
        super(message.concat("No logic found."));
    }

    public PasswordEncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordEncryptionException(Throwable cause) {
        super(cause);
    }

    public PasswordEncryptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
