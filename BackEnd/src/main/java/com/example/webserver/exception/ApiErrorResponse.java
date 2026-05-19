package com.example.webserver.exception;

import java.time.LocalDate;

public class ApiErrorResponse {
    
    private int status; 
    private String error;
    private String message;
    private LocalDate timestamp;

    public ApiErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDate.now();
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }
}
