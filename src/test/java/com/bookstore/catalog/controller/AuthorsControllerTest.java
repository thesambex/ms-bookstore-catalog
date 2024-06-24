package com.bookstore.catalog.controller;

import com.bookstore.catalog.application.services.AuthorsService;
import com.bookstore.catalog.domain.dtos.author.AuthorDetailsResponse;
import com.bookstore.catalog.domain.dtos.author.AuthorResponse;
import com.bookstore.catalog.domain.dtos.author.CreateAuthorRequest;
import com.bookstore.catalog.domain.dtos.author.UpdateAuthorRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Authors controller Test")
@WebMvcTest(controllers = AuthorsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorsService authorsService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Create author with valid request")
    @Test
    void testCreateAuthor_When_Valid_Request_Should_return_Success() throws Exception {
        CreateAuthorRequest authorRequest = new CreateAuthorRequest(UUID.randomUUID(), "J.R.R. Tolkien", "Author of The Lord Of The Rings");

        when(authorsService.createAuthor(authorRequest))
                .thenReturn(ResponseEntity.created(URI.create("/api/v1/authors/" + authorRequest.id())).body(authorRequest));

        var response = mockMvc.perform(post("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(authorRequest))
        ).andExpect(status().isCreated()).andDo(print()).andReturn().getResponse();

        CreateAuthorRequest payload = objectMapper.readValue(response.getContentAsString(), CreateAuthorRequest.class);

        Assertions.assertNotNull(payload);
        Assertions.assertNotNull(payload.id());
        Assertions.assertEquals(authorRequest.name(), payload.name());
        Assertions.assertEquals(authorRequest.biography(), payload.biography());
    }

    @DisplayName("Create author with invalid request")
    @Test
    void testCreateAuthor_When_Invalid_Request_Should_return_BadRequest() throws Exception {
        CreateAuthorRequest authorRequest = new CreateAuthorRequest(null, null, null);

        when(authorsService.createAuthor(authorRequest))
                .thenReturn(ResponseEntity.created(URI.create("/api/v1/authors/" + authorRequest.id())).body(authorRequest));

        mockMvc.perform(post("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(authorRequest))
        ).andExpect(status().isBadRequest()).andDo(print());
    }

    @DisplayName("Find author with existing ID")
    @Test
    void testFindAuthor_When_Existing_ID_Should_return_Success() throws Exception {
        UUID id = UUID.randomUUID();

        AuthorDetailsResponse author = new AuthorDetailsResponse(id, "J.R.R. Tolkien", "Foo");

        when(authorsService.getAuthor(id))
                .thenReturn(ResponseEntity.ok(author));

        var response = mockMvc.perform(get("/api/v1/authors/" + id))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse();

        AuthorDetailsResponse payload = objectMapper.readValue(response.getContentAsString(), AuthorDetailsResponse.class);

        Assertions.assertNotNull(payload);
        Assertions.assertEquals(id, payload.id());
        Assertions.assertEquals(author.name(), payload.name());
        Assertions.assertEquals(author.biography(), payload.biography());
    }

    @DisplayName("Find author with not existing ID")
    @Test
    void testFindAuthor_When_NotExisting_ID_Should_return_NotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(authorsService.getAuthor(id))
                .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/api/v1/authors/" + id))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @DisplayName("Delete author with existing ID")
    @Test
    void testDeleteAuthor_When_Existing_ID_Should_return_Success() throws Exception {
        UUID id = UUID.randomUUID();

        when(authorsService.deleteAuthor(id))
                .thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/api/v1/authors/" + id))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }

    @DisplayName("Delete author with not existing ID")
    @Test
    void testDeleteAuthor_When_NotExisting_ID_Should_return_NotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(authorsService.deleteAuthor(id))
                .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(delete("/api/v1/authors/" + id))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @DisplayName("Update author with valid request")
    @Test
    void testUpdateAuthor_With_Valid_Request_Should_return_Success() throws Exception {
        UUID id = UUID.randomUUID();

        UpdateAuthorRequest updateRequest = new UpdateAuthorRequest(
                "Foo",
                "Baa"
        );

        when(authorsService.updateAuthor(id, updateRequest))
                .thenReturn(ResponseEntity.ok(new AuthorDetailsResponse(id, updateRequest.name(), updateRequest.biography())));

        var response = mockMvc.perform(put("/api/v1/authors/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(updateRequest))
        ).andExpect(status().isOk()).andDo(print()).andReturn().getResponse();

        AuthorDetailsResponse payload = objectMapper.readValue(response.getContentAsString(), AuthorDetailsResponse.class);

        Assertions.assertNotNull(payload);
        Assertions.assertEquals(id, payload.id());
        Assertions.assertEquals(updateRequest.name(), payload.name());
        Assertions.assertEquals(updateRequest.biography(), payload.biography());
    }

    @DisplayName("Update author with invalid request")
    @Test
    void testUpdateAuthor_With_Invalid_Request_Should_return_BadRequest() throws Exception {
        UUID id = UUID.randomUUID();

        UpdateAuthorRequest updateRequest = new UpdateAuthorRequest(
                "",
                "Baa"
        );

        when(authorsService.updateAuthor(id, updateRequest))
                .thenReturn(ResponseEntity.badRequest().build());

        mockMvc.perform(put("/api/v1/authors/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(updateRequest))
        ).andExpect(status().isBadRequest()).andDo(print());
    }

    @DisplayName("List authors")
    @Test
    void testListAuthors_Should_return_Success() throws Exception {
        var authors = List.of(new AuthorResponse(UUID.randomUUID(), "Foo"));

        when(authorsService.listAll(0))
                .thenReturn(ResponseEntity.ok(new PageImpl<>(authors)));

        mockMvc.perform(get("/api/v1/authors/list?pageIndex?=0"))
                .andExpect(status().isOk()).andDo(print());
    }

}
