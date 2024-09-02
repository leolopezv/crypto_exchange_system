package org.example.service.exception;

public class InvalidCryptoEx extends RuntimeException {
    public InvalidCryptoEx() {
        super("That crypto is not supported");
    }
}
