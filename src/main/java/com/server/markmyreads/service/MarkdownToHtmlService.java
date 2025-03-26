package com.server.markmyreads.service;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public interface MarkdownToHtmlService {

    String convertMarkdownContentToHtml(@NonNull @NotBlank final String markdownContent);

}
