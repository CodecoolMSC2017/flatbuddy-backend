package com.codecool.flatbuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found!")
public class InvalidMessageSendingException extends Exception {
}
