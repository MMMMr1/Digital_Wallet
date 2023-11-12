package com.michalenok.wallet.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountServiceNotFoundException extends RuntimeException {
    public AccountServiceNotFoundException(String message) {
        super(message);
    }
}