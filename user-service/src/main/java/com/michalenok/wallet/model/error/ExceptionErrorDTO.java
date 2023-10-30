package com.michalenok.wallet.model.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExceptionErrorDTO {
    private String logref;
    private String message;

    public ExceptionErrorDTO(String message) {
        this.logref = "error";
        this.message = message;
    }
}
