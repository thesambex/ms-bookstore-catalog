package com.bookstore.catalog.domain.dtos.author;

import org.hibernate.validator.constraints.Length;

public record UpdateAuthorRequest(
        @Length(max = 60, message = "name max length is 60 characters")
        String name,
        @Length(max = 4096, message = "biography max length is 4096 characters")
        String biography
) {

}
