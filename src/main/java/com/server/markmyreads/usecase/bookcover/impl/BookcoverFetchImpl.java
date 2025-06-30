package com.server.markmyreads.usecase.bookcover.impl;

import com.server.markmyreads.domain.dto.BookcoverDto;
import com.server.markmyreads.service.BookcoverService;
import com.server.markmyreads.usecase.bookcover.BookcoverFetch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookcoverFetchImpl implements BookcoverFetch {

    private final BookcoverService bookcoverService;

    @Override
    public ResponseEntity<List<BookcoverDto>> findAll(@NonNull final MultipartFile file) {




        return ResponseEntity.ok().body(Collections.emptyList());
    }
}
