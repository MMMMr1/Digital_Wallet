package com.michalenok.wallet.model.dto.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransferNotFoundException extends RuntimeException {
    public TransferNotFoundException(String message) {
        super(message);
    }
}