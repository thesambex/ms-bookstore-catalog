package com.bookstore.catalog.domain.dtos.author;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record CreateAuthorRequest(
        UUID id,
        @NotBlank(message = "provide authors name")
        @Length(max = 60, message = "name max length is 60 characters")
        String name,
        @Length(max = 4096, message = "biography max length is 4096 characters")
        String biography
) {

}
