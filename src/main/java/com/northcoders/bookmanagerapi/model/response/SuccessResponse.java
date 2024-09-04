package com.northcoders.bookmanagerapi.model.response;

import org.springframework.http.HttpStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Setter
@Getter
public class SuccessResponse<T> extends CustomResponse {
    private T resource;
    private OperationResult operationResult;

    public static enum OperationResult {
        CREATED,
        UPDATED,
        DELETED
    }

    @Builder
    public SuccessResponse(HttpStatus status, T resource, OperationResult operationResult) {
        super(Type.SUCCESS, status, String.format("%s is %s.",
                        resource.getClass().getSimpleName(),
                        operationResult.toString().toLowerCase()
                )
        );
        this.resource = resource;
        this.operationResult = operationResult;
    }
}


