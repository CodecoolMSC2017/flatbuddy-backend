package com.codecool.flatbuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="This message has been deleted earlier!")
public class DeletedMessageException extends Exception {
}
