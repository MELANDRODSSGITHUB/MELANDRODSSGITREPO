package com.dss.auth.exception;

public class EmailAlreadyBeenUsedException extends RuntimeException {
    public EmailAlreadyBeenUsedException(String message) {
        super(message.concat("Email address is already in use by some other user."));
    }
}
