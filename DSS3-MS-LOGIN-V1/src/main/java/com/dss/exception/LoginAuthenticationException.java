package com.dss.exception;

public class LoginAuthenticationException extends RuntimeException {
    public LoginAuthenticationException(String message) {
        super(message.concat("Invalid login credentials."));
    }
}
