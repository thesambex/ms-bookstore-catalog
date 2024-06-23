package com.bookstore.catalog.domain.entities.genres;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity(name = "genre")
@Table(name = "genres", schema = "books")
public class Genre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 60)
    private String name;

    @OneToOne(mappedBy = "genre")
    private BookGenre bookGenre;

    public Genre() {

    }

    public Genre(UUID id, String name) {
        this.id = id;
        this.name = name;
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

    public BookGenre getBookGenre() {
        return bookGenre;
    }

    public void setBookGenre(BookGenre bookGenre) {
        this.bookGenre = bookGenre;
    }

}
