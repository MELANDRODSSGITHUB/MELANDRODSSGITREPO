package com.dss.exception;

public class DuplicateAdminException extends RuntimeException {
    public DuplicateAdminException(String message) {
        super(message.concat("Duplicate admin id."));
    }
}
