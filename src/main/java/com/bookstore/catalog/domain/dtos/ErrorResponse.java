package com.bookstore.catalog.domain.dtos;

import java.util.Date;

public record ErrorResponse(String message, String details, String extra, Date timestamp) {
}
