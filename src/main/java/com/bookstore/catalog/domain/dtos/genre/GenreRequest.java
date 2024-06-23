package com.bookstore.catalog.domain.dtos.genre;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record GenreRequest(
        UUID id,
        @NotBlank
        @Length(max = 60, message = "name max length is 60 characters")
        String name
) {

}
