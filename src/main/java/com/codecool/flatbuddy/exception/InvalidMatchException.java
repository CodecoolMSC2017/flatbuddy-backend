package com.codecool.flatbuddy.exception;

public class InvalidMatchException extends Exception {
    public InvalidMatchException() {
    }
    public InvalidMatchException(String message) {
        super(message);
    }

    public InvalidMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
