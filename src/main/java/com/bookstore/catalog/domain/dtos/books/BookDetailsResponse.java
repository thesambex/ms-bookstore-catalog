package com.bookstore.catalog.domain.dtos.books;

import com.bookstore.catalog.domain.dtos.author.AuthorResponse;
import com.bookstore.catalog.domain.dtos.genre.GenreResponse;
import com.bookstore.catalog.domain.entities.books.Book;
import com.bookstore.catalog.domain.entities.books.BookView;
import com.bookstore.catalog.domain.entities.genres.BookGenre;
import com.bookstore.catalog.domain.entities.genres.Genre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record BookDetailsResponse(
        UUID id,
        String name,
        String brief,
        String isbn,
        BigDecimal price,
        LocalDate publishDate,
        AuthorResponse author,
        Set<GenreResponse> genres
) {

    public static BookDetailsResponse fromBook(BookView book, Set<Genre> genres) {
        return new BookDetailsResponse(
                book.getId(),
                book.getName(),
                book.getBrief(),
                book.getIsbn(),
                book.getPrice(),
                book.getPublishDate(),
                new AuthorResponse(book.getAuthorId(), book.getAuthorName()),
                genres.stream().map(GenreResponse::fromGenre).collect(Collectors.toSet()));
    }

    public static BookDetailsResponse fromBook(Book book) {
        var genres = book.getBookGenres().stream()
                .map(BookGenre::getGenre)
                .map(GenreResponse::fromGenre)
                .collect(Collectors.toSet());

        return new BookDetailsResponse(
                book.getId(),
                book.getName(),
                book.getBrief(),
                book.getIsbn(),
                book.getPrice(),
                book.getPublishDate(),
                new AuthorResponse(book.getAuthor().getId(), book.getAuthor().getName()), genres);
    }

}
