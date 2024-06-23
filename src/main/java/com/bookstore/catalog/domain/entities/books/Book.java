package com.bookstore.catalog.domain.entities.books;

import com.bookstore.catalog.domain.entities.author.Author;
import com.bookstore.catalog.domain.entities.genres.BookGenre;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Entity(name = "book")
@Table(name = "books", schema = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2048)
    private String brief;

    @Column(name = "photo_key", length = 1024)
    private String photoKey;

    @Column(nullable = false, length = 13, unique = true)
    private String isbn;

    @Column(nullable = false)
    private BigDecimal price;

    @Temporal(TemporalType.DATE)
    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "author_id", insertable = false, updatable = false)
    private UUID authorId;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_author_id"))
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<BookGenre> bookGenres;

    public Book() {

    }

    public Book(UUID id, String name, String brief, String photoKey, String isbn, BigDecimal price, LocalDate publishDate, UUID authorId) {
        this.id = id;
        this.name = name;
        this.brief = brief;
        this.photoKey = photoKey;
        this.isbn = isbn;
        this.price = price;
        this.publishDate = publishDate;
        this.authorId = authorId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPhotoKey() {
        return photoKey;
    }

    public void setPhotoKey(String photoKey) {
        this.photoKey = photoKey;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public Collection<BookGenre> getBookGenres() {
        return bookGenres;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}
