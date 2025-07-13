package com.server.markmyreads.service;

import com.server.markmyreads.domain.enumeration.NoteStyle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public interface MarkdownToHtmlService {

    String convertMarkdownContentToHtml(@NonNull @NotBlank final String markdownContent, @NonNull @NotNull final NoteStyle style);

}
