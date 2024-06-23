package com.bookstore.catalog.application.repository.books;

import com.bookstore.catalog.application.repository.ReadOnlyRepository;
import com.bookstore.catalog.domain.entities.books.BookView;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BooksViewRepository extends ReadOnlyRepository<BookView, UUID> {

}
