package com.bookstore.catalog.domain.exceptions;


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
