package com.bookstore.catalog.controller;

import com.bookstore.catalog.application.services.GenresService;
import com.bookstore.catalog.domain.dtos.genre.GenreRequest;
import com.bookstore.catalog.domain.dtos.genre.GenreResponse;
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

@DisplayName("Genres controller Test")
@WebMvcTest(controllers = GenresController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class GenresControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenresService genresService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Create genre with valid request")
    @Test
    void testCreateGenre_When_Valid_Request_Should_return_Success() throws Exception {
        GenreRequest genreRequest = new GenreRequest(UUID.randomUUID(), "Fantasy");

        when(genresService.createGenre(genreRequest))
                .thenReturn(ResponseEntity.created(URI.create("/api/v1/genres/" + genreRequest.id())).body(genreRequest));

        var response = mockMvc.perform(post("/api/v1/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(genreRequest))
        ).andExpect(status().isCreated()).andDo(print()).andReturn().getResponse();

        GenreRequest payload = objectMapper.readValue(response.getContentAsString(), GenreRequest.class);

        Assertions.assertNotNull(payload);
        Assertions.assertNotNull(payload.id());
        Assertions.assertEquals(genreRequest.name(), payload.name());
    }

    @DisplayName("Create genre with invalid request")
    @Test
    void testCreateGenre_When_Invalid_Request_Should_return_BadRequest() throws Exception {
        GenreRequest genreRequest = new GenreRequest(null, null);

        when(genresService.createGenre(genreRequest))
                .thenReturn(ResponseEntity.created(URI.create("/api/v1/genres/" + genreRequest.id())).body(genreRequest));

        mockMvc.perform(post("/api/v1/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(genreRequest))
        ).andExpect(status().isBadRequest()).andDo(print());

    }

    @DisplayName("Find genre with existing ID")
    @Test
    void testFindGenre_When_Existing_ID_Should_return_Success() throws Exception {
        UUID id = UUID.randomUUID();
        GenreResponse genre = new GenreResponse(id, "Fantasy");

        when(genresService.getGenre(id))
                .thenReturn(ResponseEntity.ok(genre));

        var response = mockMvc.perform(get("/api/v1/genres/" + id))
                .andExpect(status().isOk()).andDo(print()).andReturn().getResponse();

        GenreResponse payload = objectMapper.readValue(response.getContentAsString(), GenreResponse.class);

        Assertions.assertNotNull(payload);
        Assertions.assertNotNull(payload.id());
        Assertions.assertEquals(genre.name(), payload.name());
    }

    @DisplayName("Find genre with not existing ID")
    @Test
    void testFindGenre_When_NotExisting_ID_Should_return_NotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(genresService.getGenre(id))
                .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/api/v1/genres/" + id))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @DisplayName("Delete genre with existing ID")
    @Test
    void testDeleteGenre_When_Existing_ID_Should_return_Success() throws Exception {
        UUID id = UUID.randomUUID();

        when(genresService.deleteGenre(id))
                .thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/api/v1/genres/" + id))
                .andExpect(status().is2xxSuccessful()).andDo(print());
    }

    @DisplayName("Delete genre with not existing ID")
    @Test
    void testDeleteGenre_When_NotExisting_ID_Should_return_NotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(genresService.deleteGenre(id))
                .thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(delete("/api/v1/genres/" + id))
                .andExpect(status().isNotFound()).andDo(print());
    }

    @DisplayName("Update genre with valid request")
    @Test
    void testUpdateGenre_When_Valid_Request_Should_return_Success() throws Exception {
        UUID id = UUID.randomUUID();

        GenreRequest updateRequest = new GenreRequest(id, "Foo");

        when(genresService.updateGenre(id, updateRequest))
                .thenReturn(ResponseEntity.ok(new GenreResponse(id, updateRequest.name())));

        var response = mockMvc.perform(put("/api/v1/genres/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(updateRequest))
        ).andExpect(status().isOk()).andDo(print()).andReturn().getResponse();

        GenreResponse payload = objectMapper.readValue(response.getContentAsString(), GenreResponse.class);

        Assertions.assertNotNull(payload);
        Assertions.assertEquals(id, payload.id());
        Assertions.assertEquals(updateRequest.name(), payload.name());
    }

    @DisplayName("Update genre with invalid request")
    @Test
    void testUpdateGenre_When_Invalid_Request_Should_return_BadRequest() throws Exception {
        UUID id = UUID.randomUUID();

        GenreRequest updateRequest = new GenreRequest(id, "");

        when(genresService.updateGenre(id, updateRequest))
                .thenReturn(ResponseEntity.ok(new GenreResponse(id, updateRequest.name())));

        mockMvc.perform(put("/api/v1/genres/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsBytes(updateRequest))
        ).andExpect(status().isBadRequest()).andDo(print());
    }

    @DisplayName("List genres")
    @Test
    void testListGenres_Should_return_Success() throws Exception {
        var genres = List.of(new GenreResponse(UUID.randomUUID(), "Fantasy"));

        when(genresService.listAll(0))
                .thenReturn(ResponseEntity.ok(new PageImpl<>(genres)));

        mockMvc.perform(get("/api/v1/genres/list?pageIndex=0"))
                .andExpect(status().isOk()).andDo(print());
    }

}
