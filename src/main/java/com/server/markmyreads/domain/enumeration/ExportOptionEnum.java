package com.server.markmyreads.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExportOptionEnum {

    MARKDWON("MARKDOWN"),
    PDF("PDF"),
    ZIP("ZIP"),;

    private final String option;
}
