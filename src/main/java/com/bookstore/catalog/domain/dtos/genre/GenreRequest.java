package com.bookstore.catalog.domain.dtos.genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record GenreRequest(
        UUID id,
        @NotBlank
        @Size(max = 60, message = "name max length is 60 characters")
        String name
) {

}
