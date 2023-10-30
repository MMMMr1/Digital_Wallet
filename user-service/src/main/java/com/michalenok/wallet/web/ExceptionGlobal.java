package com.michalenok.wallet.web;

import com.michalenok.wallet.model.error.ExceptionErrorDTO;
import com.michalenok.wallet.model.error.ExceptionListDTO;
import com.michalenok.wallet.model.error.ExceptionStructuredDTO;
import com.michalenok.wallet.model.exception.*;
import org.springframework.http.HttpStatus;
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
    @ExceptionHandler(value = {UserNotFoundException.class, VerificationUserException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionErrorDTO ArgumentUserNotFoundException(
            RuntimeException e) {
        return new ExceptionErrorDTO(e.getMessage());
    }

    /**
     * 409
     */
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionErrorDTO ArgumentUserAlreadyExistException(
            RuntimeException e) {
        return new ExceptionErrorDTO(e.getMessage());
    }

    /**
     * 500
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public List<ExceptionErrorDTO> handler(Throwable e){
        return List.of(new ExceptionErrorDTO(e.getMessage()));
    }
}
