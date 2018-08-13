package com.codecool.flatbuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason = "Access denied!")
public class InvalidMessageAccessException extends Exception {
}
