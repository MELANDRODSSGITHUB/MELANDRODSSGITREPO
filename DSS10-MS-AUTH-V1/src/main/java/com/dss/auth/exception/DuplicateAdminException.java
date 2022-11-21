package com.dss.auth.exception;

public class DuplicateAdminException extends RuntimeException {
    public DuplicateAdminException(String message) {
        super(message.concat("Duplicate admin id."));
    }
}
