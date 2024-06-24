package com.bookstore.catalog.domain.dtos.books;

import jakarta.validation.constraints.Max;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateBookRequest(
        @Length(max = 255, message = "max length is 255 characters")
        String name,
        @Length(max = 2048, message = "max length is 2048")
        String brief,
        @Length(min = 13, max = 13, message = "length is 13 characters")
        String isbn,
        @Max(999999999)
        BigDecimal price,
        LocalDate publishDate
) {
}
