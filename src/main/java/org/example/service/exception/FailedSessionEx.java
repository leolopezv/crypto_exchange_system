package org.example.service.exception;

public class FailedSessionEx extends RuntimeException {
    public FailedSessionEx(String errorMessage) {
        super(errorMessage);
    }
}
