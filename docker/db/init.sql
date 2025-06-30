
CREATE TABLE mmr_processing_history(
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    file_name TEXT,
    books_count INTEGER,
    notes_count INTEGER,
    processed_at TIMESTAMP
);

INSERT INTO mmr_processing_history(file_name, books_count, notes_count, processed_at) VALUES
    ('my_notes', 10, 150, NOW());

CREATE TABLE clippings(
    id BIGSERIAL PRIMARY KEY,
    uploaded_at TIMESTAMP NOT NULL,
    owner TEXT NULL
);

CREATE TABLE book(
   id BIGSERIAL PRIMARY KEY,
   clippings_id BIGINT NOT NULL REFERENCES clippings (id),
   title TEXT NOT NULL,
   author TEXT NOT NULL,
   last_read_at TIMESTAMP NOT NULL,

   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL
);

CREATE TABLE note(
   id BIGSERIAL PRIMARY KEY,
   book_id BIGINT NOT NULL REFERENCES book (id),
   content TEXT NOT NULL CHECK (content <> '')
);