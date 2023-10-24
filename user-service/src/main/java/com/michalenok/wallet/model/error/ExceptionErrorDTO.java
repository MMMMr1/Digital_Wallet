package com.michalenok.wallet.model.error;

public class ExceptionErrorDTO {
    private String logref;
    private String message;
    public ExceptionErrorDTO() {
    }
    public ExceptionErrorDTO(String message) {
        this.logref = "error";
        this.message = message;
    }
    public String getLogref() {
        return logref;
    }
    public String getMessage() {
        return message;
    }
}
