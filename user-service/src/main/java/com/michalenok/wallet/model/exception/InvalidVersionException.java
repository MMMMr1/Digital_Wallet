package com.michalenok.wallet.model.exception;

public class InvalidVersionException extends RuntimeException {
    public InvalidVersionException() {
    }
    public InvalidVersionException(String message) {
        super(message);
    }
}
