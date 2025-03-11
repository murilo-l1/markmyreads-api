package com.server.markmyreads.usecase.markdown;

import com.server.markmyreads.domain.enumeration.NotesSortCriteriaEnum;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface MarkdownExport {

    ResponseEntity<byte[]> convertToSingleMarkdown(@NonNull @NotNull final MultipartFile file, final NotesSortCriteriaEnum sortCriteria);

}
