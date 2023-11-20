package com.michalenok.wallet.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CurrencyCodeMismatchException extends RuntimeException {

    public CurrencyCodeMismatchException(String message) {
        super(message);
    }
}