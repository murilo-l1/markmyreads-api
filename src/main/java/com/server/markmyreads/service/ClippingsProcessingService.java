package com.server.markmyreads.service;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface ClippingsProcessingService {

    Long processAndSave(@NonNull @NotNull final MultipartFile myClippingsFile);

}
