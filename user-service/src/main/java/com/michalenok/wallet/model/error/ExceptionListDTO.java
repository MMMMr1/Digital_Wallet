package com.michalenok.wallet.model.error;

import java.util.List;

public class ExceptionListDTO {
    private String logref;
    private List<ExceptionStructuredDTO> errors;
    public ExceptionListDTO() {
    }
    public ExceptionListDTO(List<ExceptionStructuredDTO> message) {
        this.logref = "structured_error";
        this.errors = message;
    }
    public ExceptionListDTO(String logref, List<ExceptionStructuredDTO> errors) {
        this.logref = logref;
        this.errors = errors;
    }
    public String getLogref() {
        return logref;
    }
    public List<ExceptionStructuredDTO> getErrors() {
        return errors;
    }
}
