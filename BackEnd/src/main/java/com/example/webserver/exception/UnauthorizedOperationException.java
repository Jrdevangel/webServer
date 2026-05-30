package com.example.webserver.exception;

public class UnauthorizedOperationException 
        extends RuntimeException {
    
    public UnauthorizedOperationException(
            String message
    ) {
        super(message);
    }
}