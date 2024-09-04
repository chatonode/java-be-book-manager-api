package com.northcoders.bookmanagerapi.model.response;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CustomResponse {
    private Type type;
    private int code;
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;

    public static enum Type {
        SUCCESS,
        ERROR,
    }

    public CustomResponse(Type type, HttpStatus status, String message) {
        this.type = type;
        this.status = status;
        this.code = this.status.value();
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }
}
