package com.example.webserver.exception;

public class UserNotFoundException 
        extends RuntimeException {
    
    public UserNotFoundException(
            String message
    ) {
        super(message);
    }
}