package com.server.markmyreads.service;

import com.server.markmyreads.domain.jpa.Book;
import lombok.NonNull;

import java.util.List;

public interface BookService {

    List<Book> findByClippingsId(@NonNull final Long clippingsId);
}
