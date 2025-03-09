package com.server.markmyreads.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotesSortCriteriaEnum {

    BY_TITLE("BY_TITLE"),
    BY_AUTHOR("BY_AUTHOR"),
    BY_NOTES_COUNT("BY_NOTES_COUNT"),
    BY_DATE_DESC("BY_DATE_DESC");

    private final String criteria;

}
