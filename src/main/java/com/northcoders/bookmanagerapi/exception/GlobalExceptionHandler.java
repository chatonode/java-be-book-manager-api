package com.northcoders.bookmanagerapi.exception;

import com.northcoders.bookmanagerapi.model.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());

    }


    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(e.getMessage())
                .build();


        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
