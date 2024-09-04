package com.northcoders.bookmanagerapi.exception;

public class NotFoundException extends RuntimeException {
    private final String entityClassName;
    private final Object entityIdentifier;

    public NotFoundException(String entityClassName, Object entityIdentifier) {
        super(String.format("%s with ID %s was not found", entityClassName, entityIdentifier));
        this.entityClassName = entityClassName;
        this.entityIdentifier = entityIdentifier;
    }

    @Override
    public String toString() {
        return String.format("NotFoundException: %s with ID %s was not found", this.entityClassName, this.entityIdentifier);
    }
}
