
CREATE TABLE mmr_processing_history(
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    file_name TEXT,
    books_count INTEGER,
    notes_count INTEGER,
    processed_at TIMESTAMP
);

INSERT INTO mmr_processing_history(file_name, books_count, notes_count, processed_at) VALUES
    ('my_notes', 10, 150, NOW());