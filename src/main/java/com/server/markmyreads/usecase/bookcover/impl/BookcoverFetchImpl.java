package com.server.markmyreads.usecase.bookcover.impl;

import com.server.markmyreads.domain.dto.BookcoverDto;
import com.server.markmyreads.service.BookService;
import com.server.markmyreads.service.BookcoverService;
import com.server.markmyreads.usecase.bookcover.BookcoverFetch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookcoverFetchImpl implements BookcoverFetch {

    private final BookService bookService;
    private final BookcoverService bookcoverService;

    @Override
    public ResponseEntity<List<BookcoverDto>> findAll(@NonNull final Long clippingsId) {
        final List<BookcoverDto> covers = bookService.findByClippingsId(clippingsId)
                .stream().map(book -> new BookcoverDto(book.getTitle(), book.getAuthor(), null))
                .toList();

        return ResponseEntity.ok().body(covers);
    }

}
