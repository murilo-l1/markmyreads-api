package com.server.markmyreads.usecase.upload.impl;

import com.server.markmyreads.domain.dto.SessionDto;
import com.server.markmyreads.service.ClippingsProcessingService;
import com.server.markmyreads.usecase.upload.ClippingsUpload;
import com.server.markmyreads.usecase.validator.ClippingsValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ClippingsUploadImpl implements ClippingsUpload {

    private final ClippingsValidator validator;
    private final ClippingsProcessingService processingService;

    @Override
    public ResponseEntity<SessionDto> upload(@NonNull final MultipartFile myClippingsFile) {
        validator.validate(myClippingsFile);

        final Long clippingsId = processingService.processAndSave(myClippingsFile);

        return ResponseEntity
                .ok()
                .body(new SessionDto(clippingsId));
    }

}
