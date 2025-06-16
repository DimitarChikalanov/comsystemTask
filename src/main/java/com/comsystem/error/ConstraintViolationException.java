package com.comsystem.error;

import lombok.Getter;

@Getter
public class ConstraintViolationException extends RuntimeException {
    private final ErrorType errorType;

    public ConstraintViolationException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }
}
