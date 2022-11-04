package com.dss.exception;

public class AdminNotFoundException extends RuntimeException {

    private static final String ADMIN_NOT_FOUND_ERROR_MSG = "Admin not found.";

    public AdminNotFoundException() {
        super(ADMIN_NOT_FOUND_ERROR_MSG);
    }

    public AdminNotFoundException(String message) {
        super(message.concat(ADMIN_NOT_FOUND_ERROR_MSG));
    }

    public AdminNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdminNotFoundException(Throwable cause) {
        super(cause);
    }

    public AdminNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
