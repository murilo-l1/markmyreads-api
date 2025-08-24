CREATE TYPE clippings_locale AS ENUM ('PT_BR', 'EN_US');
CREATE TABLE clippings(
    id BIGSERIAL PRIMARY KEY,
    locale clippings_locale NOT NULL,
    uploaded_at TIMESTAMP NOT NULL,
    size BIGINT NOT NULL
);

CREATE TABLE book(
    id BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    author TEXT NOT NULL,
    last_read_at DATE NOT NULL
);

CREATE TABLE clippings_book (
    clippings_id BIGINT NOT NULL REFERENCES clippings (id),
    book_id BIGINT NOT NULL REFERENCES book (id)
);

CREATE TABLE note(
   id BIGSERIAL PRIMARY KEY,
   book_id BIGINT NOT NULL REFERENCES book (id),
   content TEXT NOT NULL CHECK (content <> '')
);