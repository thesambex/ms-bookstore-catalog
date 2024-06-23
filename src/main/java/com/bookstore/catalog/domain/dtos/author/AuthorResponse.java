package com.bookstore.catalog.domain.dtos.author;

import com.bookstore.catalog.domain.entities.author.Author;

import java.util.UUID;

public record AuthorResponse(UUID id, String name) {

    public static AuthorResponse fromAuthor(Author author) {
        return new AuthorResponse(author.getId(), author.getName());
    }

}
