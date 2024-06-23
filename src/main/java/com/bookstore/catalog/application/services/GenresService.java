package com.bookstore.catalog.application.services;

import com.bookstore.catalog.domain.dtos.genre.GenreRequest;
import com.bookstore.catalog.domain.dtos.genre.GenreResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface GenresService {
    ResponseEntity<GenreRequest> createGenre(GenreRequest body);

    ResponseEntity<GenreResponse> getGenre(UUID id);

    ResponseEntity<Void> deleteGenre(UUID id);

    ResponseEntity<GenreResponse> updateGenre(UUID id, GenreRequest body);

    ResponseEntity<Page<GenreResponse>> listAll(int pageIndex);
}
