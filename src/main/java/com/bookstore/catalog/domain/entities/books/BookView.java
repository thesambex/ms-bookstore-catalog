package com.bookstore.catalog.domain.entities.books;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Immutable
@Entity(name = "book_view")
@Table(name = "books_view", schema = "books")
public class BookView implements Serializable {

    @Id
    private UUID id;

    private String name;

    private String brief;

    @Column(name = "photo_key")
    private String photoKey;

    private String isbn;

    private BigDecimal price;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "author_id", nullable = false)
    private UUID authorId;

    @Column(name = "author_name")
    private String authorName;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrief() {
        return brief;
    }

    public String getPhotoKey() {
        return photoKey;
    }

    public String getIsbn() {
        return isbn;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

}
