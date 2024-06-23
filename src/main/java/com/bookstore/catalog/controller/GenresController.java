package com.bookstore.catalog.controller;

import com.bookstore.catalog.application.services.GenresService;
import com.bookstore.catalog.domain.dtos.ErrorResponse;
import com.bookstore.catalog.domain.dtos.genre.GenreRequest;
import com.bookstore.catalog.domain.dtos.genre.GenreResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/genres")
public class GenresController {

    private final GenresService genresService;

    @Autowired
    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }

    @Operation(operationId = "createGenre", summary = "Create genre", tags = {"Genres"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Genre created", content = @Content(schema = @Schema(implementation = GenreRequest.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PostMapping
    public ResponseEntity<GenreRequest> createGenre(@Valid @RequestBody GenreRequest body) {
        return genresService.createGenre(body);
    }

    @Operation(operationId = "findGenreById", summary = "Find genre by ID", tags = {"Genres"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Genre ID")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = GenreRequest.class))),
                    @ApiResponse(responseCode = "404", description = "Genre not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<GenreResponse> getGenre(@PathVariable("id") UUID id) {
        return genresService.getGenre(id);
    }

    @Operation(operationId = "deleteGenreById", summary = "Delete genre by ID", tags = "Genres",
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Genre ID")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successful operation"),
                    @ApiResponse(responseCode = "404", description = "Genre not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable("id") UUID id) {
        return genresService.deleteGenre(id);
    }

    @Operation(operationId = "updateGenreById", summary = "Update genre", tags = {"Genres"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Genre ID")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = GenreRequest.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "404", description = "Genre not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<GenreResponse> updateGenre(
            @PathVariable("id") UUID id,
            @Valid @RequestBody GenreRequest body
    ) {
        return genresService.updateGenre(id, body);
    }

    @Operation(operationId = "listGenres", summary = "List authors", tags = {"Genres"},
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page", description = "Page index")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("list")
    public ResponseEntity<Page<GenreResponse>> listGenres(@RequestParam(name = "page", defaultValue = "0") int pageIndex) {
        return genresService.listAll(pageIndex);
    }

}
