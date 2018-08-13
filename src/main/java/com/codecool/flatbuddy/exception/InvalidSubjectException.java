package com.codecool.flatbuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Subject cannot be empty!")

public class InvalidSubjectException extends Exception {

    public InvalidSubjectException() {
    }
}
