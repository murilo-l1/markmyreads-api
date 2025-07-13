package com.server.markmyreads.service;

import com.server.markmyreads.domain.enumeration.NoteStyle;
import com.server.markmyreads.domain.model.MarkMyReadsFile;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

import java.util.List;

public interface ZipService {

    byte[] zipMarkdowns(@NonNull @NotNull final List<MarkMyReadsFile> files);

    byte[] zipPdfs(@NonNull @NotNull final List<MarkMyReadsFile> files, @NonNull @NotNull final NoteStyle pdfStyle);

}
