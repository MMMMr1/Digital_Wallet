package com.michalenok.wallet.web;

import com.michalenok.wallet.model.error.ExceptionErrorDTO;
import com.michalenok.wallet.model.error.ExceptionListDTO;
import com.michalenok.wallet.model.error.ExceptionStructuredDTO;
import com.michalenok.wallet.model.exception.AccountNotFoundException;
import com.michalenok.wallet.model.exception.CurrencyCodeMismatchException;
import com.michalenok.wallet.model.exception.ExceedingBalanceLimitException;
import com.michalenok.wallet.model.exception.InsufficientFundsException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionGlobal {
    /**
     * 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionListDTO onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return new ExceptionListDTO(e.getBindingResult().getFieldErrors().stream()
                .map(s -> new ExceptionStructuredDTO(s.getField(), s.getDefaultMessage()))
                .collect(Collectors.toList()));
    }
    /**
     * 400
     */
    @ExceptionHandler(value = {ExceedingBalanceLimitException.class, InsufficientFundsException.class, CurrencyCodeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionErrorDTO ArgumentTransferException(
            RuntimeException e) {
        return new ExceptionErrorDTO(e.getMessage());
    }

    /**
     * 404
     */
    @ExceptionHandler(value = AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionErrorDTO ArgumentAccountNotFoundException(
            RuntimeException e) {
        return new ExceptionErrorDTO(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public  ExceptionErrorDTO handleAccessDeniedException(AccessDeniedException e) {
        return new ExceptionErrorDTO(e.getMessage());
    }

    /**
     * 500
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public List<ExceptionErrorDTO> handler(Throwable e) {
        return List.of(new ExceptionErrorDTO(e.getMessage()));
    }
}
