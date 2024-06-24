package com.bookstore.catalog.controller;

import com.bookstore.catalog.application.services.BooksService;
import com.bookstore.catalog.domain.dtos.author.AuthorResponse;
import com.bookstore.catalog.domain.dtos.books.BookDetailsResponse;
import com.bookstore.catalog.domain.dtos.books.BookResponse;
import com.bookstore.catalog.domain.dtos.books.CreateBookRequest;
import com.bookstore.catalog.domain.dtos.books.UpdateBookRequest;
import com.bookstore.catalog.domain.dtos.genre.GenreResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Books controller Test")
@WebMvcTest(controllers = BooksController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BooksService booksService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void setup() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @DisplayName("Create book with valid request")
    @Test
    void testCreateBook_When_Valid_Request_Should_return_Success() throws Exception {
        CreateBookRequest bookRequest = new CreateBookRequest(
                UUID.randomUUID(),
                "The Hobbit",
                null,
                "9788595084742",
                new BigDecimal("23.50"),
                LocalDate.now(),
                UUID.randomUUID());

        when(booksService.createBook(bookRequest))
                .thenReturn(ResponseEntity.created(URI.create("/api/v1/books/" + bookRequest.id())).body(bookRequest));

        var response = mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(bookRequest))
        ).andExpect(status().isCreated()).andDo(print()).andReturn().getResponse();

        CreateBookRequest payload = objectMapper.readValue(response.getContentAsString(), CreateBookRequest.class);

        Assertions.assertNotNull(payload);
        Assertions.assertNotNull(payload.id());
        Assertions.assertEquals(bookRequest.name(), payload.name());
        Assertions.assertEquals(bookRequest.brief(), payload.brief());
        Assertions.assertEquals(bookRequest.isbn(), payload.isbn());
        Assertions.assertEquals(bookRequest.price(), payload.price());
        Assertions.assertEquals(bookRequest.publishDate(), payload.publishDate());
        Assertions.assertEquals(bookRequest.authorId(), payload.authorId());
    }

    @DisplayName("Create book with invalid request")
    @Test
    void testCreateBook_When_Invalid_Request_Should_return_Success() throws Exception {
        CreateBookRequest bookRequest = new CreateBookRequest(
                UUID.randomUUID(),
                "",
                null,
                "978859508474",
                new BigDecimal("23.50"),
                LocalDate.now(),
                UUID.randomUUID());

        when(booksService.createBook(bookRequest))
                .thenReturn(ResponseEntity.created(URI.create("/api/v1/books/" + bookRequest.id())).body(bookRequest));

        mockMvc.perform(post("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(bookRequest))
        ).andExpect(status().isBadRequest()).andDo(print());
    }

    @DisplayName("Find book with existing ID")
    @Test
    void testFindBook_When_Existing_ID_Should_return_Success() throws Exception {
        UUID id = UUID.randomUUID();

        BookDetailsResponse book = new BookDetailsResponse(
                id,
                "Foo",
                "Bar",
                "1234567891234",
                new BigDecimal("12.90"),
                LocalDate.now(),
                new AuthorResponse(UUID.randomUUID(), "Foo of Silva"),
                Set.of(new GenreResponse(UUID.randomUUID(), "Fantasy"))
        );

        when(booksService.getBook(id))
                .thenReturn(ResponseEntity.ok(book));

        var response = mockMvc.perform(get("/api/v1/books/" + id))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse();

        BookDetailsResponse payload = objectMapper.readValue(response.getContentAsString(), BookDetailsResponse.class);

        Assertions.assertNotNull(payload);
        Assertions.assertEquals(id, payload.id());
        Assertions.assertEquals(book.name(), payload.name());
        Assertions.assertEquals(book.brief(), payload.brief());
        Assertions.assertEquals(book.isbn(), payload.isbn());
        Assertions.assertEquals(book.price(), payload.price());
        Assertions.assertEquals(book.author(), payload.author());
        Assertions.assertEquals(book.genres(), payload.genres());
    }

    @DisplayName("Find book with not existing ID")
    @Test
    void testFindBook_When_NotExisting_ID_Should_return_NotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(booksService.getBook(id))
                .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/api/v1/books/" + id))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @DisplayName("Delete book with existing ID")
    @Test
    void testDeleteBook_When_Existing_ID_Should_return_Success() throws Exception {
        UUID id = UUID.randomUUID();

        when(booksService.deleteBook(id))
                .thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/api/v1/books/" + id))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }

    @DisplayName("Delete book with not existing ID")
    @Test
    void testDeleteBook_When_NotExisting_ID_Should_return_NotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(booksService.deleteBook(id))
                .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(delete("/api/v1/books/" + id))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @DisplayName("Update book with valid request")
    @Test
    void testUpdateBook_When_Valid_Request_Should_return_Success() throws Exception {
        UUID id = UUID.randomUUID();

        UpdateBookRequest updateRequest = new UpdateBookRequest(
                "Foo",
                "Bar",
                "1234567891234",
                new BigDecimal("12.90"),
                LocalDate.now()
        );

        BookDetailsResponse bookResponse = new BookDetailsResponse(
                id,
                updateRequest.name(),
                updateRequest.brief(),
                updateRequest.isbn(),
                updateRequest.price(),
                updateRequest.publishDate(),
                new AuthorResponse(UUID.randomUUID(), "Foo of Silva"),
                Set.of(new GenreResponse(UUID.randomUUID(), "Fantasy"))
        );

        when(booksService.updateBook(id, updateRequest))
                .thenReturn(ResponseEntity.ok(bookResponse));

        var response = mockMvc.perform(put("/api/v1/books/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(updateRequest))
        ).andExpect(status().isOk()).andDo(print()).andReturn().getResponse();

        BookDetailsResponse payload = objectMapper.readValue(response.getContentAsString(), BookDetailsResponse.class);

        Assertions.assertNotNull(payload);
        Assertions.assertEquals(id, payload.id());
        Assertions.assertEquals(bookResponse.name(), payload.name());
        Assertions.assertEquals(bookResponse.brief(), payload.brief());
        Assertions.assertEquals(bookResponse.isbn(), payload.isbn());
        Assertions.assertEquals(bookResponse.price(), payload.price());
        Assertions.assertEquals(bookResponse.author(), payload.author());
        Assertions.assertEquals(bookResponse.genres(), payload.genres());
    }

    @DisplayName("Update book with invalid request")
    @Test
    void testUpdateBook_When_Invalid_Request_Should_return_BadRequest() throws Exception {
        UUID id = UUID.randomUUID();

        UpdateBookRequest updateRequest = new UpdateBookRequest(
                "",
                "Bar",
                "12345",
                new BigDecimal("12.90"),
                LocalDate.now()
        );

        when(booksService.updateBook(id, updateRequest))
                .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(put("/api/v1/books/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(updateRequest))
        ).andExpect(status().isBadRequest()).andDo(print());
    }

    @DisplayName("List books")
    @Test
    void testListBooks_Should_return_Success() throws Exception {
        var books = List.of(new BookResponse(UUID.randomUUID(), "Foo", "Foo of Baa"));

        when(booksService.listAll(0))
                .thenReturn(ResponseEntity.ok(new PageImpl<>(books)));

        mockMvc.perform(get("/api/v1/books/list?pageIndex=0"))
                .andExpect(status().isOk()).andDo(print());
    }

    @DisplayName("Add book genre")
    @Test
    void testAddBookGenre_Should_return_Success() throws Exception {
        UUID bookId = UUID.randomUUID();
        UUID genreId = UUID.randomUUID();

        when(booksService.addBookGenre(bookId, genreId))
                .thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(post("/api/v1/books/" + bookId + "/genres/" + genreId + "/add"))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }

    @DisplayName("Remove book genre")
    @Test
    void testRemoveBookGenre_Should_return_Success() throws Exception {
        UUID bookId = UUID.randomUUID();
        UUID genreId = UUID.randomUUID();

        when(booksService.removeBookGenre(bookId, genreId))
                .thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/api/v1/books/" + bookId + "/genres/" + genreId + "/remove"))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }

}
