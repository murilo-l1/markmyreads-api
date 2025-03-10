package com.server.markmyreads.service;

import com.server.markmyreads.domain.dto.ClippingsContext;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClippingsExtractorService {

    ClippingsContext extractClippingsBlocks(@NonNull @NotNull final MultipartFile file);

}
