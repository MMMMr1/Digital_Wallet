package com.michalenok.wallet.model.exception;

public class VerificationUserException extends IllegalArgumentException {
    public VerificationUserException() {
    }
    public VerificationUserException(String message) {
        super(message);
    }
}
