package com.bookstore.catalog.domain.dtos;

import java.time.LocalDateTime;

public record ErrorResponse(String message, String details, String extra, LocalDateTime timestamp) {
}
