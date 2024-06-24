package com.bookstore.catalog.domain.entities.genres;

import com.bookstore.catalog.domain.entities.books.Book;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity(name = "book_genre")
@Table(name = "books_genre", schema = "books")
public class BookGenre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "book_id", nullable = false, insertable = false, updatable = false)
    private UUID bookId;

    @Column(name = "genre_id", nullable = false, insertable = false, updatable = false)
    private UUID genreId;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_book_id"))
    private Book book;

    @OneToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_genre_id"))
    private Genre genre;

    public BookGenre() {

    }

    public BookGenre(UUID id, UUID bookId, UUID genreId) {
        this.id = id;
        this.bookId = bookId;
        this.genreId = genreId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getBookId() {
        return bookId;
    }

    public UUID getGenreId() {
        return genreId;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
