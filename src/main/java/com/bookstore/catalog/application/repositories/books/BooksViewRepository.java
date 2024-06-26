package com.bookstore.catalog.application.repositories.books;

import com.bookstore.catalog.application.repositories.ReadOnlyRepository;
import com.bookstore.catalog.domain.entities.books.BookView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BooksViewRepository extends ReadOnlyRepository<BookView, UUID> {
    @Query(
            value = "SELECT b FROM book_view b WHERE b.name LIKE %:query%",
            countQuery = "SELECT COUNT(b) FROM book_view b WHERE b.name LIKE %:query%"
    )
    Page<BookView> search(@Param("query") String query, Pageable pageable);

    Optional<BookView> findByIsbn(String isbn);
}
