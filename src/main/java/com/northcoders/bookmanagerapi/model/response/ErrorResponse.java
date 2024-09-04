package com.northcoders.bookmanagerapi.model.response;

import org.springframework.http.HttpStatus;
import lombok.Builder;

public class ErrorResponse extends CustomResponse {

    @Builder
    public ErrorResponse(HttpStatus status, String message) {
        super(Type.ERROR, status, message);
    }
}
