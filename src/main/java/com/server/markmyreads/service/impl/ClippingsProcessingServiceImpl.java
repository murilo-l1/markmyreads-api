package com.server.markmyreads.service.impl;


import com.server.markmyreads.domain.component.ClippingBlockParser;
import com.server.markmyreads.domain.dto.ClippingsContext;
import com.server.markmyreads.domain.dto.ParsedClippingDto;
import com.server.markmyreads.domain.jpa.Book;
import com.server.markmyreads.domain.jpa.Clippings;
import com.server.markmyreads.domain.jpa.Note;
import com.server.markmyreads.repository.BookRepository;
import com.server.markmyreads.repository.NoteRepository;
import com.server.markmyreads.service.ClippingsExtractorService;
import com.server.markmyreads.service.ClippingsProcessingService;
import com.server.markmyreads.service.ClippingsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClippingsProcessingServiceImpl implements ClippingsProcessingService {

    private final ClippingsExtractorService extractor;
    private final ClippingBlockParser parser;

    private final ClippingsService clippingsService;
    private final BookRepository bookRepository;
    private final NoteRepository noteRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long processAndSave(@NonNull final MultipartFile myClippingsFile) {
        final ClippingsContext context = extractor.extractClippingsBlocks(myClippingsFile);

        final Clippings clippings = clippingsService.save(myClippingsFile, context.locale());

        final Map<String, Book> processedBooksCache = new HashMap<>();

        final List<ParsedClippingDto> notes = context.blocks().stream()
                .map(parser::parse)
                .filter(Objects::nonNull)
                .toList();

        if (notes.isEmpty()) {
            return clippings.getId();
        }

        Note noteToEvaluate = null;
        for (final ParsedClippingDto note : notes) {
            final Book book = findOrCreateBook(clippings, processedBooksCache, note);
            updateLastReadAt(book, note.date());

            if (noteToEvaluate == null) {
                noteToEvaluate = new Note(book, note.content());
                continue;
            }

            boolean fromSameBook = noteToEvaluate.getBook().getId().equals(book.getId());
            boolean currentContainsPrevious = note.content().contains(noteToEvaluate.getContent());

            if (fromSameBook && currentContainsPrevious) {
                noteToEvaluate.setContent(note.content());
            }
            else {
                noteRepository.save(noteToEvaluate);
                noteToEvaluate = new Note(book, note.content());
            }
        }

        noteRepository.save(noteToEvaluate);

        return clippings.getId();
    }

    private Book findOrCreateBook(final Clippings clippings, Map<String, Book> cache, ParsedClippingDto dto) {
        return cache.computeIfAbsent(dto.title(), title ->
                bookRepository.findByTitleAndAuthor(title, dto.author())
                        .orElseGet(() -> {
                            Book newBook = new Book(clippings, title, dto.author());
                            newBook.setLastReadAt(dto.date());
                            return bookRepository.save(newBook);
                        })
        );
    }

    private void updateLastReadAt(Book book, LocalDate date) {
        if (book.getLastReadAt() == null || date.isAfter(book.getLastReadAt())) {
            book.setLastReadAt(date);
        }
    }

}
