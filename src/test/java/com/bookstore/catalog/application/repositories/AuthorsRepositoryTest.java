package com.bookstore.catalog.application.repositories;

import com.bookstore.catalog.domain.entities.author.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DisplayName("Authors repository Test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorsRepositoryTest {

    @Autowired
    private AuthorsRepository authorsRepository;

    @DisplayName("Save author")
    @Test
    void testGivenAuthorObject_WhenSave_Should_return_SavedAuthor() {
        Author author = new Author(null, "J.R.R. Tolkien", "The lord of rings");

        Author savedAuthor = authorsRepository.save(author);

        Assertions.assertNotNull(savedAuthor);
        Assertions.assertNotNull(savedAuthor.getId());
        Assertions.assertEquals(author.getName(), savedAuthor.getName());
        Assertions.assertEquals(author.getBiography(), savedAuthor.getBiography());
    }

    @DisplayName("Find author by ID")
    @Test
    void testWhenFindAuthorByID_Should_return_Author() {
        Author toSaveAuthor = authorsRepository.save(new Author(null, "Foo", "An Author"));

        Author savedAuthor = authorsRepository.findById(toSaveAuthor.getId()).orElse(null);

        Assertions.assertNotNull(savedAuthor);
        Assertions.assertEquals(toSaveAuthor.getName(), savedAuthor.getName());
        Assertions.assertEquals(toSaveAuthor.getBiography(), savedAuthor.getBiography());
    }

    @DisplayName("Delete author")
    @Test
    void testWhenDeleteAuthor_Should_return_Null() {
        Author author = authorsRepository.save(new Author(null, "Foo", "Author biography"));

        authorsRepository.delete(author);

        Author savedAuthor = authorsRepository.findById(author.getId()).orElse(null);

        Assertions.assertNull(savedAuthor);
    }

    @DisplayName("Update author")
    @Test
    void testWhenUpdateAuthor_Should_SaveChanges() {
        Author author = authorsRepository.save(new Author(null, "Foo", "An Author"));

        author.setName("J.R.R. Tolkien");
        author.setBiography("Author of the lord of rings");

        Author savedAuthor = authorsRepository.save(author);

        Assertions.assertNotNull(savedAuthor);
        Assertions.assertEquals("J.R.R. Tolkien", author.getName());
        Assertions.assertEquals("Author of the lord of rings", author.getBiography());
    }

    @DisplayName("List authors paginated")
    @Test
    void testWhenListAuthorsPaginated_Should_return_Page() {
        authorsRepository.save(new Author(null, "Foo", "Author biography"));
        authorsRepository.save(new Author(null, "J.R.R. Tolkien", "The lord of rings"));

        Page<Author> authors = authorsRepository.findAll(PageRequest.of(1, 10));

        Assertions.assertNotNull(authors);
        Assertions.assertEquals(2, authors.getTotalElements());
    }

}
