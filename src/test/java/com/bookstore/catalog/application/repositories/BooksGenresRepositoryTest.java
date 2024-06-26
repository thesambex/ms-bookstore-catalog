package com.bookstore.catalog.application.repositories;

import com.bookstore.catalog.application.repositories.books.BooksRepository;
import com.bookstore.catalog.application.repositories.genres.BooksGenresRepository;
import com.bookstore.catalog.application.repositories.genres.GenresRepository;
import com.bookstore.catalog.domain.entities.author.Author;
import com.bookstore.catalog.domain.entities.books.Book;
import com.bookstore.catalog.domain.entities.genres.BookGenre;
import com.bookstore.catalog.domain.entities.genres.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@DisplayName("Books genres repository Test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BooksGenresRepositoryTest {

    @Autowired
    private BooksGenresRepository booksGenresRepository;

    private static Book book;
    private static Genre genre;

    @BeforeAll
    static void setup(
            @Autowired AuthorsRepository authorsRepository,
            @Autowired BooksRepository booksRepository,
            @Autowired GenresRepository genresRepository
    ) {
        Author author = authorsRepository.save(new Author(null, "Tolkien", ""));

        book = new Book(null, "The Hobbit", "Foo", null, "1234567891234", new BigDecimal("10.0"), LocalDate.now());
        book.setAuthor(author);

        book = booksRepository.save(book);
        genre = genresRepository.save(new Genre(null, "Fantasy"));
    }

    @DisplayName("Save book genre")
    @Test
    void testGivenBookGenreObject_Should_return_SavedBookGenre() {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setBook(book);
        bookGenre.setGenre(genre);

        BookGenre savedBookGenre = booksGenresRepository.save(bookGenre);

        Assertions.assertNotNull(savedBookGenre);
        Assertions.assertNotNull(savedBookGenre.getId());
    }

    @DisplayName("Find book genre by book ID and genre ID")
    @Test
    void testWhenFindBookGenreByBookIdAndGenreId_Should_return_BookGenre() {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setBook(book);
        bookGenre.setGenre(genre);

        booksGenresRepository.save(bookGenre);

        BookGenre savedBookGenre = booksGenresRepository.findByBookIdAndGenreId(book.getId(), genre.getId()).orElse(null);

        Assertions.assertNotNull(savedBookGenre);
        Assertions.assertEquals(bookGenre.getBook().getId(), savedBookGenre.getBook().getId());
        Assertions.assertEquals(bookGenre.getGenre().getId(), savedBookGenre.getGenre().getId());
    }

    @DisplayName("Delete book genre")
    @Test
    void testWhenDeleteBookGenre_Should_return_Null() {
        BookGenre toSaveBookGenre = new BookGenre();
        toSaveBookGenre.setBook(book);
        toSaveBookGenre.setGenre(genre);

        toSaveBookGenre = booksGenresRepository.save(toSaveBookGenre);

        booksGenresRepository.delete(toSaveBookGenre);

        BookGenre savedBookGenre = booksGenresRepository.findByBookIdAndGenreId(toSaveBookGenre.getBook().getId(), toSaveBookGenre.getGenreId()).orElse(null);

        Assertions.assertNull(savedBookGenre);
    }

}
