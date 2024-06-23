package com.bookstore.catalog.domain.dtos.books;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CreateBookRequest(
        UUID id,
        @NotBlank(message = "provide book name")
        @Length(max = 255, message = "max length is 255 characters")
        String name,
        @Length(max = 2048, message = "max length is 2048")
        String brief,
        @NotBlank
        @Length(min = 13, max = 13, message = "length is 13 characters")
        String isbn,
        @NotNull(message = "provide book price")
        @Max(999999999)
        BigDecimal price,
        @NotNull(message = "provide publishDate")
        LocalDate publishDate,
        @NotNull(message = "provide authorId")
        UUID authorId
) {
}
