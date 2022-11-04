package com.dss.exception;

public class LoginAuthenticationException extends RuntimeException {
    public LoginAuthenticationException() {
        super();
    }

    public LoginAuthenticationException(String message) {
        super(message.concat("Invalid login credentials."));
    }

    public LoginAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAuthenticationException(Throwable cause) {
        super(cause);
    }

    public LoginAuthenticationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
