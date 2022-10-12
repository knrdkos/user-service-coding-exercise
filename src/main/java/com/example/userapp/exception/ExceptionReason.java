package com.example.userapp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ExceptionReason {
    USER_ALREADY_EXISTS("user with provided email address already exists", HttpStatus.BAD_REQUEST),
    USER_DOESNT_EXISTS("user does not exist", HttpStatus.BAD_REQUEST),
    USER_UNAUTHORIZED("user unauthorized", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;
}
