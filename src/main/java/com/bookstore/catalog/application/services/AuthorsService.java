package com.bookstore.catalog.application.services;

import com.bookstore.catalog.domain.dtos.author.CreateAuthorRequest;
import com.bookstore.catalog.domain.dtos.author.UpdateAuthorRequest;
import com.bookstore.catalog.domain.dtos.author.AuthorDetailsResponse;
import com.bookstore.catalog.domain.dtos.author.AuthorResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AuthorsService {
    ResponseEntity<CreateAuthorRequest> createAuthor(CreateAuthorRequest body);

    ResponseEntity<AuthorDetailsResponse> getAuthor(UUID id);

    ResponseEntity<Void> deleteAuthor(UUID id);

    ResponseEntity<AuthorDetailsResponse> updateAuthor(UUID id, UpdateAuthorRequest body);

    ResponseEntity<Page<AuthorResponse>> listAll(int pageIndex);
}
