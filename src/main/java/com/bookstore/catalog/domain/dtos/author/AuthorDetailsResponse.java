package com.bookstore.catalog.domain.dtos.author;

import java.util.UUID;

public record AuthorDetailsResponse(UUID id, String name, String biography) {
}
