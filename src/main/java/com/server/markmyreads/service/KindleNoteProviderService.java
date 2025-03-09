package com.server.markmyreads.service;

import com.server.markmyreads.domain.enumeration.NotesSortCriteriaEnum;
import com.server.markmyreads.domain.model.KindleNote;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface KindleNoteProviderService {

    List<KindleNote> proccessAllNotesBySort(@NonNull @NotNull final MultipartFile file, final NotesSortCriteriaEnum criteria);

}
