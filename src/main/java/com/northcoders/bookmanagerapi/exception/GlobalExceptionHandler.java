package com.northcoders.bookmanagerapi.exception;

import com.northcoders.bookmanagerapi.model.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())

                .build();

        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }


    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();


        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
