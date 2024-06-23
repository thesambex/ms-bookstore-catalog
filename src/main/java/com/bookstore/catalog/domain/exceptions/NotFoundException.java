package com.bookstore.catalog.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private String extra;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, String extra) {
        super(message);
        this.extra = extra;
    }

    public String getExtra() {
        return extra;
    }

}
