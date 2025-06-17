package com.comsystem.controller;

import com.comsystem.error.ConstraintViolationException;
import com.comsystem.error.ErrorType;
import com.comsystem.model.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(
            ConstraintViolationException ex) {
        ErrorType errorType = ex.getErrorType();
        ErrorResponseDto error =
                new ErrorResponseDto(
                        errorType.name(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        String errorCodeString =
                ex.getBindingResult().getFieldErrors().stream()
                        .findFirst()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .orElse("GB_500");

        log.debug("Validation failed: {}", errorCodeString);
        ErrorType errorCode = ErrorType.valueOf(errorCodeString);

        ErrorResponseDto errorResponse =
                new ErrorResponseDto(
                        errorCode.name(), HttpStatus.BAD_REQUEST.value(), errorCode.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneral(Exception ex) {
        log.error("Something went wrong {}", ex.getMessage());
        ErrorResponseDto error =
                new ErrorResponseDto(
                        ErrorType.GB_500.name(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ErrorType.GB_500.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
