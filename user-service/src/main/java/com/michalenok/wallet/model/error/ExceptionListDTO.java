package com.michalenok.wallet.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionListDTO {
    private String logref;
    private List<ExceptionStructuredDTO> errors;

    public ExceptionListDTO(List<ExceptionStructuredDTO> message) {
        this.logref = "structured_error";
        this.errors = message;
    }
}
