package com.bookstore.catalog.application.repositories;

import com.bookstore.catalog.application.repositories.books.BooksRepository;
import com.bookstore.catalog.domain.entities.author.Author;
import com.bookstore.catalog.domain.entities.books.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

@DisplayName("Books repository Test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BooksRepositoryTest {

    @Autowired
    private BooksRepository booksRepository;

    private static Author author;

    @BeforeAll
    static void setup(@Autowired AuthorsRepository authorsRepository) {
        author = authorsRepository.save(new Author(null, "Tolkien", ""));
    }

    @DisplayName("Save book")
    @Test
    void testGivenBookObject_Should_return_SavedBook() {
        Book book = new Book(null, "The Hobbit", "Foo", null, "1234567891234", new BigDecimal("10.0"), LocalDate.now());
        book.setAuthor(author);

        Book savedBook = booksRepository.save(book);

        Assertions.assertNotNull(savedBook);
        Assertions.assertNotNull(savedBook.getId());
        Assertions.assertEquals(book.getName(), savedBook.getName());
        Assertions.assertEquals(book.getBrief(), savedBook.getBrief());
        Assertions.assertEquals(book.getIsbn(), savedBook.getIsbn());
        Assertions.assertEquals(book.getPhotoKey(), savedBook.getPhotoKey());
        Assertions.assertEquals(book.getPrice(), savedBook.getPrice());
        Assertions.assertEquals(book.getAuthor().getId(), savedBook.getAuthor().getId());
    }

    @DisplayName("Find book by ID")
    @Test
    void testWhenFindBookByID_Should_return_Book() {
        Book toSaveBook = new Book(
                null,
                "The Hobbit",
                "Foo",
                null,
                "1234567891234",
                new BigDecimal("10.0"),
                LocalDate.now()
        );

        toSaveBook.setAuthor(author);

        toSaveBook = booksRepository.save(toSaveBook);

        Book savedBook = booksRepository.findById(toSaveBook.getId()).orElse(null);

        Assertions.assertNotNull(savedBook);
        Assertions.assertNotNull(savedBook.getId());
        Assertions.assertEquals(toSaveBook.getName(), savedBook.getName());
        Assertions.assertEquals(toSaveBook.getBrief(), savedBook.getBrief());
        Assertions.assertEquals(toSaveBook.getIsbn(), savedBook.getIsbn());
        Assertions.assertEquals(toSaveBook.getPhotoKey(), savedBook.getPhotoKey());
        Assertions.assertEquals(toSaveBook.getPrice(), savedBook.getPrice());
        Assertions.assertEquals(toSaveBook.getAuthor().getId(), savedBook.getAuthor().getId());
    }

    @DisplayName("Find book by ISBN")
    @Test
    void testWhenFindBookByISBN_Should_return_Book() {
        Book toSaveBook = new Book(
                null,
                "The Hobbit",
                "Foo",
                null,
                "1234567891234",
                new BigDecimal("10.0"),
                LocalDate.now()
        );

        toSaveBook.setAuthor(author);

        booksRepository.save(toSaveBook);

        Book savedBook = booksRepository.findByIsbn("1234567891234").orElse(null);

        Assertions.assertNotNull(savedBook);
        Assertions.assertNotNull(savedBook.getId());
        Assertions.assertEquals(toSaveBook.getName(), savedBook.getName());
        Assertions.assertEquals(toSaveBook.getBrief(), savedBook.getBrief());
        Assertions.assertEquals(toSaveBook.getIsbn(), savedBook.getIsbn());
        Assertions.assertEquals(toSaveBook.getPhotoKey(), savedBook.getPhotoKey());
        Assertions.assertEquals(toSaveBook.getPrice(), savedBook.getPrice());
        Assertions.assertEquals(toSaveBook.getAuthor().getId(), savedBook.getAuthor().getId());
    }

    @DisplayName("Delete book")
    @Test
    void testWhenDeleteBook_Should_return_Null() {
        Book toSaveBook = new Book(
                null,
                "The Hobbit",
                "Foo",
                null,
                "1234567891234",
                new BigDecimal("10.0"),
                LocalDate.now()
        );

        toSaveBook.setAuthor(author);

        toSaveBook = booksRepository.save(toSaveBook);

        booksRepository.delete(toSaveBook);

        Book savedBook = booksRepository.findById(toSaveBook.getId()).orElse(null);

        Assertions.assertNull(savedBook);
    }

    @DisplayName("Update book")
    @Test
    void testWhenUpdateBook_Should_SaveChanges() {
        Book book = new Book(
                null,
                "The Hobbit",
                "Foo",
                null,
                "1234567891234",
                new BigDecimal("10.0"),
                LocalDate.now()
        );

        book.setAuthor(author);

        book = booksRepository.save(book);

        book.setBrief("The book");
        book.setPrice(new BigDecimal("20.50"));

        Book savedBook = booksRepository.save(book);

        Assertions.assertNotNull(savedBook);
        Assertions.assertNotNull(savedBook.getId());
        Assertions.assertEquals(book.getName(), savedBook.getName());
        Assertions.assertEquals(book.getBrief(), savedBook.getBrief());
        Assertions.assertEquals(book.getIsbn(), savedBook.getIsbn());
        Assertions.assertEquals(book.getPhotoKey(), savedBook.getPhotoKey());
        Assertions.assertEquals(book.getPrice(), savedBook.getPrice());
        Assertions.assertEquals(book.getAuthor().getId(), savedBook.getAuthor().getId());
    }

    @DisplayName("List books paginated")
    @Test
    void testWhenListBooksPaginated_Should_return_Page() {
        Book book1 = new Book(
                null,
                "The Hobbit",
                "Foo",
                null,
                "1234567891234",
                new BigDecimal("10.0"),
                LocalDate.now()
        );
        book1.setAuthor(author);

        Book book2 = new Book(
                null,
                "Foo",
                "Foo",
                null,
                "1234567891235",
                new BigDecimal("10.0"),
                LocalDate.now()
        );
        book2.setAuthor(author);

        booksRepository.save(book1);
        booksRepository.save(book2);

        Page<Book> books = booksRepository.findAll(PageRequest.of(0, 10));

        Assertions.assertNotNull(books);
        Assertions.assertEquals(2, books.getTotalElements());
    }

}
