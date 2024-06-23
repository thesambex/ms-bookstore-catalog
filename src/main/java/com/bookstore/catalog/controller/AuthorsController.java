package com.bookstore.catalog.controller;

import com.bookstore.catalog.application.services.AuthorsService;
import com.bookstore.catalog.domain.dtos.author.CreateAuthorRequest;
import com.bookstore.catalog.domain.dtos.author.UpdateAuthorRequest;
import com.bookstore.catalog.domain.dtos.author.AuthorDetailsResponse;
import com.bookstore.catalog.domain.dtos.author.AuthorResponse;
import com.bookstore.catalog.domain.dtos.ErrorResponse;
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
@RequestMapping("api/v1/authors")
public class AuthorsController {

    private final AuthorsService authorsService;

    @Autowired
    public AuthorsController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @Operation(operationId = "createAuthor", summary = "Create a book author", tags = {"Authors"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Author created", content = @Content(schema = @Schema(implementation = CreateAuthorRequest.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping
    public ResponseEntity<CreateAuthorRequest> createAuthor(@Valid @RequestBody CreateAuthorRequest body) {
        return authorsService.createAuthor(body);
    }

    @Operation(operationId = "findAuthorById", summary = "Find author by ID", tags = {"Authors"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Author ID")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = AuthorDetailsResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<AuthorDetailsResponse> getAuthor(@PathVariable("id") UUID id) {
        return authorsService.getAuthor(id);
    }

    @Operation(operationId = "deleteAuthorById", summary = "Delete author by ID", tags = {"Authors"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Author ID")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successful operation"),
                    @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable("id") UUID id) {
        return authorsService.deleteAuthor(id);
    }

    @Operation(operationId = "updateAuthorById", summary = "Update author", tags = {"Authors"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Author ID")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = AuthorDetailsResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<AuthorDetailsResponse> updateAuthor(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateAuthorRequest body
    ) {
        return authorsService.updateAuthor(id, body);
    }

    @Operation(operationId = "listAuthors", summary = "List authors", tags = {"Authors"},
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page", description = "Page index")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("list")
    public ResponseEntity<Page<AuthorResponse>> listAuthors(@RequestParam(name = "page", defaultValue = "0") int pageIndex) {
        return authorsService.listAll(pageIndex);
    }

}
