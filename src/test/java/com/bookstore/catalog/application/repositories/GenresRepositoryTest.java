package com.bookstore.catalog.application.repositories;

import com.bookstore.catalog.application.repositories.genres.GenresRepository;
import com.bookstore.catalog.domain.entities.genres.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayName("Genres repository Test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GenresRepositoryTest {

    @Autowired
    private GenresRepository genresRepository;

    @DisplayName("Save genre")
    @Test
    void testGivenGenreObject_WhenSave_Should_return_SavedGenre() {
        Genre genre = new Genre(null, "Fantasy");

        Genre savedGenre = genresRepository.save(genre);

        Assertions.assertNotNull(savedGenre);
        Assertions.assertNotNull(savedGenre.getId());
        Assertions.assertEquals(genre.getName(), savedGenre.getName());
    }

    @DisplayName("Find genre by ID")
    @Test
    void testWhenFindGenreByID_Should_return_Genre() {
        Genre toSaveGenre = genresRepository.save(new Genre(null, "Foo"));

        Genre savedGenre = genresRepository.findById(toSaveGenre.getId()).orElse(null);

        Assertions.assertNotNull(savedGenre);
        Assertions.assertEquals(savedGenre.getName(), toSaveGenre.getName());
    }

    @DisplayName("Delete genre")
    @Test
    void testWhenDeleteGenre_Should_return_Null() {
        Genre genre = genresRepository.save(new Genre(null, "Baa"));

        genresRepository.delete(genre);

        Genre savedGenre = genresRepository.findById(genre.getId()).orElse(null);

        Assertions.assertNull(savedGenre);
    }

    @DisplayName("Update genre")
    @Test
    void testWhenUpdateGenre_Should_SaveChanges() {
        Genre genre = genresRepository.save(new Genre(null, "Foo"));

        genre.setName("Fantasy");

        Genre savedGenre = genresRepository.save(genre);

        Assertions.assertNotNull(savedGenre);
        Assertions.assertEquals("Fantasy", savedGenre.getName());
    }

    @DisplayName("List genres paginated")
    @Test
    void testWhenListGenresPaginated_Should_return_Page() {
        genresRepository.save(new Genre(null, "Fantasy"));
        genresRepository.save(new Genre(null, "Adventure"));

        Page<Genre> genres = genresRepository.findAll(PageRequest.of(0, 10));

        Assertions.assertNotNull(genres);
        Assertions.assertEquals(2, genres.getTotalElements());
    }

}
