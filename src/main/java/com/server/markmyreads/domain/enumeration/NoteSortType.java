package com.server.markmyreads.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NoteSortType {

    TITLE("TITLE"),
    AUTHOR("AUTHOR"),
    NOTES_COUNT("NOTES_COUNT"),
    DATE_DESC("DATE_DESC");

    private final String criteria;

}
