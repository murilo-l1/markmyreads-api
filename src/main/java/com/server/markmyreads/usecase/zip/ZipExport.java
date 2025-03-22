package com.server.markmyreads.usecase.zip;

import com.server.markmyreads.domain.enumeration.NoteSortType;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ZipExport {

    ResponseEntity<byte[]> convertToManyMarkdowns(@NonNull @NotNull final MultipartFile file, @NonNull @NotNull final NoteSortType sort);

}
