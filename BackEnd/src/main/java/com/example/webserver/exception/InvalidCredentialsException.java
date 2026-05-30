package com.example.webserver.exception;

public class InvalidCredentialsException 
        extends RuntimeException {
    
    public InvalidCredentialsException(
            String message
    ) {
        super(message);
    }    
}