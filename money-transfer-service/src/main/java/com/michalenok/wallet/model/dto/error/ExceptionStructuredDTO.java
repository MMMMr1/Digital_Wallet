package com.michalenok.wallet.model.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionStructuredDTO {
    private String field;
    private String message;

    public ExceptionStructuredDTO(String message) {
        this.field = "structured_error";
        this.message = message;
    }
}