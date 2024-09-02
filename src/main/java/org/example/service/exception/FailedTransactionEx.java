package org.example.service.exception;

public class FailedTransactionEx extends RuntimeException {
    public FailedTransactionEx(String errorMessage) {
        super(errorMessage);
    }
}
