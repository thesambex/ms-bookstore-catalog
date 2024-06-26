package com.bookstore.catalog.domain.entities.author;

import com.bookstore.catalog.domain.entities.books.Book;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Entity(name = "author")
@Table(name = "authors", schema = "books")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(length = 4096)
    private String biography;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "author")
    private Collection<Book> books;

    public Author() {

    }

    public Author(UUID id, String name, String biography) {
        this.id = id;
        this.name = name;
        this.biography = biography;
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

}
