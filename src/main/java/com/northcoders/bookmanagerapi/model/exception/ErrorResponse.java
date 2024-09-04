package com.northcoders.bookmanagerapi.model.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String error;
    private String message;
}
