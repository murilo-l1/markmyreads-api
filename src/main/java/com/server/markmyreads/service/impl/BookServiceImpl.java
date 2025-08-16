package com.server.markmyreads.service.impl;

import com.server.markmyreads.domain.jpa.Book;
import com.server.markmyreads.repository.BookRepository;
import com.server.markmyreads.service.BookService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Override
    public List<Book> findByClippingsId(final @NonNull Long clippingsId) {
        return repository.findByClippingsId(clippingsId);
    }

}
