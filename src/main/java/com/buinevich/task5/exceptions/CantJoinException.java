package com.buinevich.task5.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CantJoinException extends RuntimeException {

    public CantJoinException(String message) {
        super(message);
    }
}
