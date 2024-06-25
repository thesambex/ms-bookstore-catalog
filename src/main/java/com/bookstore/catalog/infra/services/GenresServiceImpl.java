package com.bookstore.catalog.infra.services;

import com.bookstore.catalog.application.repositories.genres.GenresRepository;
import com.bookstore.catalog.application.services.GenresService;
import com.bookstore.catalog.domain.dtos.genre.GenreRequest;
import com.bookstore.catalog.domain.dtos.genre.GenreResponse;
import com.bookstore.catalog.domain.entities.genres.Genre;
import com.bookstore.catalog.domain.exceptions.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;

@Service
public class GenresServiceImpl implements GenresService {

    private final GenresRepository genresRepository;

    @Autowired
    public GenresServiceImpl(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    @Override
    public ResponseEntity<GenreRequest> createGenre(GenreRequest body) {
        Genre genre = new Genre();
        BeanUtils.copyProperties(body, genre);

        genre.setId(null);
        genre = genresRepository.save(genre);

        var response = new GenreRequest(genre.getId(), genre.getName());

        return ResponseEntity.created(URI.create("/api/v1/genres/" + response.id())).body(response);
    }

    @Override
    public ResponseEntity<GenreResponse> getGenre(UUID id) {
        Genre genre = genresRepository.findById(id).orElse(null);
        if (genre == null) {
            throw new NotFoundException("Genre " + id + " not found");
        }

        return ResponseEntity.ok(new GenreResponse(genre.getId(), genre.getName()));
    }

    @Override
    public ResponseEntity<Void> deleteGenre(UUID id) {
        Genre genre = genresRepository.findById(id).orElse(null);
        if (genre == null) {
            throw new NotFoundException("Genre " + id + " not found");
        }

        genresRepository.delete(genre);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<GenreResponse> updateGenre(UUID id, GenreRequest body) {
        Genre genre = genresRepository.findById(id).orElse(null);
        if (genre == null) {
            throw new NotFoundException("Genre " + id + " not found");
        }

        if(!body.name().isBlank())
            genre.setName(body.name());

        genresRepository.save(genre);

        return ResponseEntity.ok(new GenreResponse(genre.getId(), genre.getName()));
    }

    @Override
    public ResponseEntity<Page<GenreResponse>> listAll(int pageIndex) {
        Page<Genre> genres = genresRepository.findAll(PageRequest.of(pageIndex, 10));
        return ResponseEntity.ok(genres.map(GenreResponse::fromGenre));
    }

}
