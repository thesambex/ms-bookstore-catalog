package com.bookstore.catalog.application.repository.genres;

import com.bookstore.catalog.domain.entities.genres.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GenresRepository extends JpaRepository<Genre, UUID> {
}
