package com.bookstore.catalog.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

    private String extra;

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, String extra) {
        super(message);
        this.extra = extra;
    }

    public String getExtra() {
        return extra;
    }

}
