package com.bookstore.catalog.application.repositories.genres;

import com.bookstore.catalog.domain.entities.genres.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BooksGenresRepository extends JpaRepository<BookGenre, UUID> {
    List<BookGenre> findByBookId(UUID bookId);

    Optional<BookGenre> findByBookIdAndGenreId(UUID bookId, UUID genreId);
}
