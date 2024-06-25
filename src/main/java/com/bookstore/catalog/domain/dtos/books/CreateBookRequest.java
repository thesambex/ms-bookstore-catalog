package com.bookstore.catalog.domain.dtos.books;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateBookRequest(
        UUID id,
        @NotBlank(message = "provide book name")
        @Size(max = 255, message = "max length is 255 characters")
        String name,
        @Size(max = 2048, message = "max length is 2048")
        String brief,
        @NotBlank(message = "provide book isbn")
        @Size(min = 13, max = 13, message = "length is 13 characters")
        String isbn,
        @NotNull(message = "provide book price")
        @DecimalMax(value = "999999999.00")
        BigDecimal price,
        @NotNull(message = "provide publishDate")
        LocalDate publishDate,
        @NotNull(message = "provide authorId")
        UUID authorId
) {
}
