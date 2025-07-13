package com.server.markmyreads.service.impl;

import com.server.markmyreads.domain.component.ClippingBlockParser;
import com.server.markmyreads.domain.dto.ClippingsContext;
import com.server.markmyreads.domain.jpa.Book;
import com.server.markmyreads.domain.jpa.Clippings;
import com.server.markmyreads.domain.jpa.Note;
import com.server.markmyreads.repository.BookRepository;
import com.server.markmyreads.repository.ClippingsRepository;
import com.server.markmyreads.repository.NoteRepository;
import com.server.markmyreads.service.ClippingsExtractorService;
import com.server.markmyreads.service.ClippingsProcessingService;
import com.server.markmyreads.service.ClippingsService;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        ClippingsContext context = extractor.extractClippingsBlocks(myClippingsFile);

        Clippings clippings = clippingsService.save(myClippingsFile, context.locale());

        Map<String, Book> processedBooksCache = new HashMap<>();

        context.blocks().stream()
                .map(parser::parse)
                .filter(Objects::nonNull)
                .forEach(parsedDto -> {

                    Book book = processedBooksCache.computeIfAbsent(parsedDto.title(), title ->
                            bookRepository.findByTitleAndAuthor(title, parsedDto.author())
                                    .orElseGet(() -> {
                                        Book newBook = new Book(clippings, title, parsedDto.author());
                                        newBook.setLastReadAt(parsedDto.date());
                                        return bookRepository.save(newBook);
                                    })
                    );

                    if (book.getLastReadAt() == null || parsedDto.date().isAfter(book.getLastReadAt())) {
                        book.setLastReadAt(parsedDto.date());
                    }

                    Note note = new Note(book, parsedDto.content());
                    noteRepository.save(note);
                });

        return clippings.getId();
    }

}
