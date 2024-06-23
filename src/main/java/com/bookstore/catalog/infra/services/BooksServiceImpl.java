package com.bookstore.catalog.infra.services;

import com.bookstore.catalog.application.repository.books.BooksRepository;
import com.bookstore.catalog.application.repository.books.BooksViewRepository;
import com.bookstore.catalog.application.repository.genres.BooksGenresRepository;
import com.bookstore.catalog.application.services.BooksService;
import com.bookstore.catalog.domain.dtos.books.BookDetailsResponse;
import com.bookstore.catalog.domain.dtos.books.BookResponse;
import com.bookstore.catalog.domain.dtos.books.CreateBookRequest;
import com.bookstore.catalog.domain.dtos.books.UpdateBookRequest;
import com.bookstore.catalog.domain.entities.author.Author;
import com.bookstore.catalog.domain.entities.books.Book;
import com.bookstore.catalog.domain.entities.books.BookView;
import com.bookstore.catalog.domain.entities.genres.BookGenre;
import com.bookstore.catalog.domain.exceptions.ConflictException;
import com.bookstore.catalog.domain.exceptions.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BooksServiceImpl implements BooksService {

    private final BooksRepository booksRepository;
    private final BooksViewRepository booksViewRepository;
    private final BooksGenresRepository booksGenresRepository;

    @Autowired
    public BooksServiceImpl(
            BooksRepository booksRepository,
            BooksViewRepository booksViewRepository,
            BooksGenresRepository booksGenresRepository
    ) {
        this.booksRepository = booksRepository;
        this.booksViewRepository = booksViewRepository;
        this.booksGenresRepository = booksGenresRepository;
    }

    @Override
    public ResponseEntity<CreateBookRequest> createBook(CreateBookRequest body) {
        Book book = new Book();
        BeanUtils.copyProperties(body, book);

        if (booksRepository.findBookByIsbn(body.isbn()).isPresent()) {
            throw new ConflictException("Already exists a book with this isbn", "isbn");
        }

        Author author = new Author();
        author.setId(body.authorId());

        book.setId(null);
        book.setAuthor(author);

        book = booksRepository.save(book);

        var response = new CreateBookRequest(
                book.getId(),
                book.getName(),
                book.getBrief(),
                book.getIsbn(),
                book.getPrice(),
                book.getPublishDate(),
                book.getAuthorId()
        );

        return ResponseEntity.created(URI.create("/api/v1/books/" + response.id())).body(response);
    }

    @Override
    public ResponseEntity<BookDetailsResponse> getBook(UUID id) {
        BookView book = booksViewRepository.findById(id).orElse(null);
        if (book == null) {
            throw new NotFoundException("Book " + id + " not found");
        }

        var genres = booksGenresRepository.findByBookId(id).stream()
                .map(BookGenre::getGenre)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(BookDetailsResponse.fromBook(book, genres));
    }

    @Override
    public ResponseEntity<Void> deleteBook(UUID id) {
        Book book = booksRepository.findById(id).orElse(null);
        if (book == null) {
            throw new NotFoundException("Book " + id + " not found");
        }

        booksRepository.delete(book);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<BookDetailsResponse> updateBook(UUID id, UpdateBookRequest body) {
        Book book = booksRepository.findById(id).orElse(null);
        if (book == null) {
            throw new NotFoundException("Book " + id + " not found");
        }

        if (!body.name().isBlank())
            book.setName(body.name());

        if (!body.brief().isBlank())
            book.setBrief(body.brief());

        if (!body.isbn().isBlank() && !body.isbn().equals(book.getIsbn())) {
            if (booksRepository.findBookByIsbn(body.isbn()).isPresent()) {
                throw new ConflictException("Already exists a book with this isbn", "isbn");
            }
            book.setIsbn(body.isbn());
        }

        if (body.price() != null)
            book.setPrice(body.price());

        if (body.publishDate() != null)
            book.setPublishDate(body.publishDate());

        booksRepository.save(book);

        return ResponseEntity.ok(BookDetailsResponse.fromBook(book));
    }

    @Override
    public ResponseEntity<Page<BookResponse>> listAll(int pageIndex) {
        Page<BookView> books = booksViewRepository.findAll(PageRequest.of(pageIndex, 10));
        return ResponseEntity.ok(books.map(BookResponse::fromBook));
    }

}
