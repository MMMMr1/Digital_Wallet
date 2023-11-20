package com.michalenok.wallet.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExceedingBalanceLimitException extends RuntimeException {
    public ExceedingBalanceLimitException(String message) {
        super(message);
    }
}