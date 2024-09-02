package org.example.service.exception;

public class InvalidWalletEx extends RuntimeException {
    public InvalidWalletEx() {
        super("The wallet could not be found");
    }
}
