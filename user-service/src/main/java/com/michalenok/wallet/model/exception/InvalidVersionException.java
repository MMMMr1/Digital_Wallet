package com.michalenok.wallet.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidVersionException extends RuntimeException {
    public InvalidVersionException(String message) {
        super(message);
    }
}
