package com.bookstore.catalog.domain.dtos.books;

import com.bookstore.catalog.domain.entities.books.Book;
import com.bookstore.catalog.domain.entities.books.BookView;

import java.math.BigDecimal;
import java.util.UUID;

public record BookResponse(
        UUID id,
        String name,
        BigDecimal price,
        String authorName
) {

    public static BookResponse fromBook(BookView book) {
        return new BookResponse(book.getId(), book.getName(), book.getPrice(), book.getAuthorName());
    }

    public static BookResponse fromBook(Book book) {
        return new BookResponse(book.getId(), book.getName(), book.getPrice(), book.getAuthor().getName());
    }

}
