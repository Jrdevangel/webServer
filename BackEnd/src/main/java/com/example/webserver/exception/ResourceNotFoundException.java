package com.example.webserver.exception;

public class ResourceNotFoundException 
        extends RuntimeException {
    
    public ResourceNotFoundException(
            String message
    ) {
        super(message);
    }
}