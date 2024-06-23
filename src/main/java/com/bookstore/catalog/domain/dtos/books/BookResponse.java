package com.bookstore.catalog.domain.dtos.books;

import com.bookstore.catalog.domain.entities.books.Book;
import com.bookstore.catalog.domain.entities.books.BookView;

import java.util.UUID;

public record BookResponse(
        UUID id,
        String name,
        String authorName
) {

    public static BookResponse fromBook(BookView book) {
        return new BookResponse(book.getId(), book.getName(), book.getAuthorName());
    }

    public static BookResponse fromBook(Book book) {
        return new BookResponse(book.getId(), book.getName(), book.getAuthor().getName());
    }

}
