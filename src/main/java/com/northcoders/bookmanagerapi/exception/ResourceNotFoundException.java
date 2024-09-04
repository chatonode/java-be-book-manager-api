package com.northcoders.bookmanagerapi.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String entityClassName, Object entityIdentifier) {
        super(String.format("%s with ID %s was not found.", entityClassName, entityIdentifier));
    }
}
