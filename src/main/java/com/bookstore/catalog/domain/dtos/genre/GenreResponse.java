package com.bookstore.catalog.domain.dtos.genre;

import com.bookstore.catalog.domain.entities.genres.Genre;

import java.util.UUID;

public record GenreResponse(UUID id, String name) {

    public static GenreResponse fromGenre(Genre genre) {
        return new GenreResponse(genre.getId(), genre.getName());
    }

}
