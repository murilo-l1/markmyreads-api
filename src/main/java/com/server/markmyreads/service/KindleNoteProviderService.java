package com.server.markmyreads.service;

import com.server.markmyreads.domain.dto.ClippingsContext;
import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.domain.model.KindleNote;
import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface KindleNoteProviderService {

    List<KindleNote> processAllNotesBySort(@NonNull @NotNull final ClippingsContext context, @NonNull @NotNull final NoteSortType type);

    List<String> provideMetadata(@NonNull @NotNull final ClippingsContext context);

}
