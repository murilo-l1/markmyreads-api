package com.server.markmyreads.usecase.pdf;

import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.domain.enumeration.NoteStyleEnum;
import com.server.markmyreads.domain.payload.NoteFormatOptionsPayload;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface PdfExport {

    ResponseEntity<byte[]> convertToSinglePdf(@NonNull @NotNull final MultipartFile file,
                                              @NonNull @NotNull final NoteFormatOptionsPayload payload);

}
