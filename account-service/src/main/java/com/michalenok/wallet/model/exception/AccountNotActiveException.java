package com.michalenok.wallet.model.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountNotActiveException extends RuntimeException {
    public AccountNotActiveException(String message) {
        super(message);
    }
}