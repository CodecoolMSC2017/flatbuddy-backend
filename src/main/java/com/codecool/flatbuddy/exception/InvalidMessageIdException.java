package com.codecool.flatbuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "There's no message with this ID!")
public class InvalidMessageIdException extends Exception {

    public InvalidMessageIdException() {}
}
