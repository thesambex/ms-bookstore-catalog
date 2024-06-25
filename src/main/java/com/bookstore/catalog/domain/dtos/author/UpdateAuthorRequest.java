package com.bookstore.catalog.domain.dtos.author;

import jakarta.validation.constraints.Size;

public record UpdateAuthorRequest(
        @Size(max = 60, message = "name max length is 60 characters")
        String name,
        @Size(max = 4096, message = "biography max length is 4096 characters")
        String biography
) {

}
