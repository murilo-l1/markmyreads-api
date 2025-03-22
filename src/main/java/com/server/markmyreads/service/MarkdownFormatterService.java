package com.server.markmyreads.service;

import com.server.markmyreads.domain.model.KindleNote;
import com.server.markmyreads.domain.model.MarkMyReadsFile;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.util.List;

public interface MarkdownFormatterService {

    MarkMyReadsFile formatToSingleMarkdown(@NonNull @NotNull final List<KindleNote> notes);

    List<MarkMyReadsFile> formatToManyMarkdowns(@NonNull @NotNull final List<KindleNote> notes);
}
