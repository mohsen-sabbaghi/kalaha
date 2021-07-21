package com.bol.kalaha.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class IllegalMoveException extends RuntimeException {
    public IllegalMoveException(String message) {
        super("Invalid Move: " + message);
    }
}
