package com.bookstore.catalog.application.services;

import com.bookstore.catalog.domain.dtos.books.BookDetailsResponse;
import com.bookstore.catalog.domain.dtos.books.BookResponse;
import com.bookstore.catalog.domain.dtos.books.CreateBookRequest;
import com.bookstore.catalog.domain.dtos.books.UpdateBookRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface BooksService {
    ResponseEntity<CreateBookRequest> createBook(CreateBookRequest body);

    ResponseEntity<BookDetailsResponse> getBook(UUID id);

    ResponseEntity<Void> deleteBook(UUID id);

    ResponseEntity<BookDetailsResponse> updateBook(UUID id, UpdateBookRequest body);

    ResponseEntity<Page<BookResponse>> listAll(int pageIndex);

    ResponseEntity<Void> addBookGenre(UUID bookId, UUID genreId);

}
