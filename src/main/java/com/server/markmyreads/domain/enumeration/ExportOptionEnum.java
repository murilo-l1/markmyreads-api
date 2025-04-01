package com.server.markmyreads.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExportOptionEnum {

    MARKDOWN("MARKDOWN"),
    PDF("PDF"),
    ZIP_MARKDOWNS("ZIP_MARKDOWNS"),
    ZIP_PDFS("ZIP_PDFS");

    private final String option;
}
