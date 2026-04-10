package com.example.webserver.exception;

public class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String message) {
            super(message);
        }  
    }
