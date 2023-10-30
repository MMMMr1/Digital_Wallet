package com.michalenok.wallet.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ValidationUserException extends IllegalArgumentException {
    public ValidationUserException(String message) {
        super(message);
    }
}
