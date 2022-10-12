package com.example.userapp.exception;

import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorWrapper> handleAppException(AppException exception) {
        return new ResponseEntity<>(
                new ErrorWrapper(exception.getReason().getMessage()),
                exception.getReason().getStatus()
        );
    }

    @Value
    static class ErrorWrapper {
        String message;
    }
}
