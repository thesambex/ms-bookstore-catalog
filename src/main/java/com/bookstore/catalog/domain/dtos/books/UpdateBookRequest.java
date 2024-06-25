package com.bookstore.catalog.domain.dtos.books;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateBookRequest(
        @Size(max = 255, message = "max length is 255 characters")
        String name,
        @Size(max = 2048, message = "max length is 2048")
        String brief,
        @Size(min = 13, max = 13, message = "length is 13 characters")
        String isbn,
        @DecimalMax(value = "999999999.00")
        BigDecimal price,
        LocalDate publishDate
) {
}
