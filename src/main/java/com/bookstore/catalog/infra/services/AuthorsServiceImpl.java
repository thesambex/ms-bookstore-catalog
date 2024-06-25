package com.bookstore.catalog.infra.services;

import com.bookstore.catalog.application.repositories.AuthorsRepository;
import com.bookstore.catalog.application.services.AuthorsService;
import com.bookstore.catalog.domain.dtos.author.CreateAuthorRequest;
import com.bookstore.catalog.domain.dtos.author.UpdateAuthorRequest;
import com.bookstore.catalog.domain.dtos.author.AuthorDetailsResponse;
import com.bookstore.catalog.domain.dtos.author.AuthorResponse;
import com.bookstore.catalog.domain.entities.author.Author;
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
public class AuthorsServiceImpl implements AuthorsService {

    private final AuthorsRepository authorsRepository;

    @Autowired
    public AuthorsServiceImpl(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    @Override
    public ResponseEntity<CreateAuthorRequest> createAuthor(CreateAuthorRequest body) {
        Author author = new Author();
        BeanUtils.copyProperties(body, author);

        author.setId(null);
        author = authorsRepository.save(author);

        var response = new CreateAuthorRequest(author.getId(), author.getName(), author.getBiography());

        return ResponseEntity.created(URI.create("/api/v1/authors/" + response.id())).body(response);
    }

    @Override
    public ResponseEntity<AuthorDetailsResponse> getAuthor(UUID id) {
        Author author = authorsRepository.findById(id).orElse(null);
        if (author == null) {
            throw new NotFoundException("Author " + id + " not found");
        }

        var response = new AuthorDetailsResponse(author.getId(), author.getName(), author.getBiography());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteAuthor(UUID id) {
        Author author = authorsRepository.findById(id).orElse(null);
        if (author == null) {
            throw new NotFoundException("Author " + id + " not found");
        }

        authorsRepository.delete(author);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AuthorDetailsResponse> updateAuthor(UUID id, UpdateAuthorRequest body) {
        Author author = authorsRepository.findById(id).orElse(null);
        if (author == null) {
            throw new NotFoundException("Author " + id + " not found");
        }

        if (!body.name().isBlank())
            author.setName(body.name());

        if (!body.biography().isBlank())
            author.setBiography(body.biography());

        authorsRepository.save(author);

        var response = new AuthorDetailsResponse(author.getId(), author.getName(), author.getBiography());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Page<AuthorResponse>> listAll(int pageIndex) {
        Page<Author> authors = authorsRepository.findAll(PageRequest.of(pageIndex, 10));
        return ResponseEntity.ok(authors.map(AuthorResponse::fromAuthor));
    }

}
