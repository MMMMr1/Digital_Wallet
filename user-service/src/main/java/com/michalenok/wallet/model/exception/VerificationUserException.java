package com.michalenok.wallet.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VerificationUserException extends IllegalArgumentException {
    public VerificationUserException(String message) {
        super(message);
    }
}
