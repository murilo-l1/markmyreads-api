package com.server.markmyreads.service;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClippingsExtractorService {

    List<String> extractClippingsBlocks(@NonNull @NotNull final MultipartFile file);

}
