package com.server.markmyreads.usecase.zip;

import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.domain.enumeration.NoteStyle;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ZipExport {

    ResponseEntity<byte[]> convertToManyMarkdowns(@NonNull @NotNull final MultipartFile file, @NonNull @NotNull final NoteSortType sort);

    ResponseEntity<byte[]> convertToManyPdfs(@NonNull @NotNull final MultipartFile file,
                                             @NonNull @NotNull final NoteSortType sort,
                                             @NonNull @NotNull final NoteStyle style);
}
