package com.northcoders.bookmanagerapi.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    public ResourceAlreadyExistsException(String entityClassName, Object entityIdentifier) {
        super(String.format("%s with ID %s already exists", entityClassName, entityIdentifier));
    }
}
