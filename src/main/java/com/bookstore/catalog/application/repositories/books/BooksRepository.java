package com.bookstore.catalog.application.repositories.books;

import com.bookstore.catalog.domain.entities.books.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BooksRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findBookByIsbn(String isbn);
}
