package com.bookstore.catalog.controller;

import com.bookstore.catalog.application.services.BooksService;
import com.bookstore.catalog.domain.dtos.ErrorResponse;
import com.bookstore.catalog.domain.dtos.books.BookDetailsResponse;
import com.bookstore.catalog.domain.dtos.books.BookResponse;
import com.bookstore.catalog.domain.dtos.books.CreateBookRequest;
import com.bookstore.catalog.domain.dtos.books.UpdateBookRequest;
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
@RequestMapping("api/v1/books")
public class BooksController {

    private final BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @Operation(operationId = "createBook", summary = "Create book", tags = {"Books"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Book created", content = @Content(schema = @Schema(implementation = CreateBookRequest.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PostMapping
    public ResponseEntity<CreateBookRequest> createBook(@Valid @RequestBody CreateBookRequest body) {
        return booksService.createBook(body);
    }

    @Operation(operationId = "findBookById", summary = "Find book by ID", tags = {"Books"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Book id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = BookDetailsResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @GetMapping("{id}")
    public ResponseEntity<BookDetailsResponse> getBook(@PathVariable("id") UUID id) {
        return booksService.getBook(id);
    }

    @Operation(operationId = "deleteBookById", summary = "Delete book by ID", tags = {"Books"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Book id")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successful operation"),
                    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") UUID id) {
        return booksService.deleteBook(id);
    }

    @Operation(operationId = "updateBookById", summary = "Update book", tags = {"Books"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "id", description = "Book id")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = BookDetailsResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request"),
                    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<BookDetailsResponse> updateBook(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UpdateBookRequest body
    ) {
        return booksService.updateBook(id, body);
    }

    @Operation(operationId = "listBooks", summary = "List books", tags = {"Books"},
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page", description = "Page index")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("list")
    public ResponseEntity<Page<BookResponse>> listBooks(@RequestParam(name = "page", defaultValue = "0") int pageIndex) {
        return booksService.listAll(pageIndex);
    }

    @Operation(operationId = "addBookGenre", summary = "Add book genre", tags = {"Books"},
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "bookId", description = "Book ID"),
                    @Parameter(in = ParameterIn.PATH, name = "genreId", description = "Genre ID")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successful operation"),
                    @ApiResponse(responseCode = "404", description = "Book or Genre not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PostMapping("{bookId}/genres/{genreId}/add")
    public ResponseEntity<Void> addGenre(
            @PathVariable("bookId") UUID bookId,
            @PathVariable("genreId") UUID genreId
    ) {
        return booksService.addBookGenre(bookId, genreId);
    }

}
