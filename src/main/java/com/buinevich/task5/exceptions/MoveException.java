package com.buinevich.task5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MoveException extends RuntimeException {

    public MoveException(String message) {
        super(message);
    }
}
