CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA books;

CREATE TABLE IF NOT EXISTS books.authors(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(60) NOT NULL,
    biography VARCHAR(4096)
);

CREATE TABLE IF NOT EXISTS books.genres(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(60) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS books.books(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    brief VARCHAR(2048),
    photo_key VARCHAR(1024),
    isbn VARCHAR(13) NOT NULL UNIQUE,
    price DECIMAL(12,2),
    publish_date DATE NOT NULL,
    author_id UUID NOT NULL,
    CONSTRAINT fk_author_id FOREIGN KEY(author_id) REFERENCES books.authors(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS books.books_genre(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    book_id UUID NOT NULL,
    genre_id UUID NOT NULL,
    CONSTRAINT fk_book_id FOREIGN KEY(book_id) REFERENCES books.books(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_genre_id FOREIGN KEY(genre_id) REFERENCES books.genres(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE VIEW books.books_view AS
    SELECT
        b.id,
        b.name,
        b.brief,
        b.photo_key,
        b.isbn,
        b.price,
        b.publish_date,
        au.id AS author_id,
        au.name AS author_name
FROM books.books b
INNER JOIN books.authors au ON au.id = b.author_id;