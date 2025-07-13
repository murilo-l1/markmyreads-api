package com.server.markmyreads.service.impl;

import com.server.markmyreads.domain.enumeration.ClippingsLocale;
import com.server.markmyreads.domain.jpa.Clippings;
import com.server.markmyreads.repository.ClippingsRepository;
import com.server.markmyreads.service.ClippingsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClippingsServiceImpl implements ClippingsService {

    private final ClippingsRepository repository;

    @Override
    public Clippings save(@NonNull final MultipartFile file, final ClippingsLocale locale) {
        final Clippings clippings = new Clippings();

        clippings.setLocale(locale);
        clippings.setUploadedAt(LocalDateTime.now());
        clippings.setSize(file.getSize());

        return repository.save(clippings);
    }

}
