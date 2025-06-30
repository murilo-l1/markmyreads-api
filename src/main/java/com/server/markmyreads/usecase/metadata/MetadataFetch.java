package com.server.markmyreads.usecase.metadata;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MetadataFetch {

    ResponseEntity<List<String>> fetch (@NotNull final MultipartFile clippings);

}
