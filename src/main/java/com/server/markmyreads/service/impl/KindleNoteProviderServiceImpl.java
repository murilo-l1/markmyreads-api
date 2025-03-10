package com.server.markmyreads.service.impl;

import com.server.markmyreads.domain.constant.ClippingsConstants;
import com.server.markmyreads.domain.dto.ClippingsContext;
import com.server.markmyreads.domain.enumeration.NotesSortCriteriaEnum;
import com.server.markmyreads.domain.model.KindleNote;
import com.server.markmyreads.service.KindleNoteProviderService;
import com.server.markmyreads.util.DateParserUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.server.markmyreads.domain.constant.ClippingsConstants.AUTHOR_NOT_PROVIDED;


@Service("KindleNoteProviderService")
public class KindleNoteProviderServiceImpl implements KindleNoteProviderService {

    private static final Pattern AUTHOR_PATTERN = Pattern.compile("\\(([^)]+)\\)");

    @Override
    public List<KindleNote> processAllNotesBySort(final ClippingsContext context, final NotesSortCriteriaEnum criteria) {

        final List<String> blocks = context.blocks();

        final Map<String, KindleNote> noteMap = new HashMap<>();

        for (final String block : blocks) {

            final String[] blockLines = block.split("\n");

            if (blockLines.length < ClippingsConstants.NOTE_CONTENT_INDEX + 1) continue;

            final String header = blockLines[ClippingsConstants.HEADER_INDEX];
            final String title = extractTitleFromHeader(header);
            final String author = extractAuthorFromHeader(header);
            final String note = blockLines[ClippingsConstants.NOTE_CONTENT_INDEX];
            final LocalDate date = DateParserUtil.parseToLocalDate(blockLines[ClippingsConstants.LAST_DATE_READ_INDEX]);

            noteMap.compute(title, (key, existingNote) -> {

                if (existingNote == null) {
                    return new KindleNote(title, author, note, date);
                }
                else {
                    existingNote.addNote(note);
                    existingNote.updateLastReadAt(date);
                    return existingNote;
                }

            });
        }

        final List<KindleNote> notes = cleanUpNotes(noteMap.values());

        return applySort(notes, criteria);
    }

    private List<KindleNote> applySort(final List<KindleNote> notes, final NotesSortCriteriaEnum criteria) {

        if (criteria == null) {
            return notes;
        }

        switch (criteria) {

            case BY_DATE_DESC -> notes.sort(Comparator.comparing(KindleNote::getLastReadAt, Comparator.nullsLast(Comparator.reverseOrder())));

            case BY_NOTES_COUNT -> notes.sort(Comparator.comparing(KindleNote::notesCount).reversed());

            case BY_TITLE ->  notes.sort(Comparator.comparing(KindleNote::getTitle));

            case BY_AUTHOR -> notes.sort(Comparator.comparing(KindleNote::getAuthor));
        }

        return notes;
    }

    private List<KindleNote> cleanUpNotes(final Collection<KindleNote> notes) {

        notes.forEach(KindleNote::removeDuplicateNotes);
        return new ArrayList<>(notes);
    }

    private String extractTitleFromHeader(final String header) {

        if (StringUtils.isBlank(header)) {
            return "";
        }

        int lastOpenParenIndex = header.lastIndexOf("(");
        return lastOpenParenIndex == -1
                ? header.trim()
                : header.substring(0, lastOpenParenIndex).trim();
    }

    private String extractAuthorFromHeader(final String header) {

        if (StringUtils.isBlank(header)) {
            return AUTHOR_NOT_PROVIDED;
        }

        int lastOpenParenIndex = header.lastIndexOf("(");
        if (lastOpenParenIndex == -1) {
            return AUTHOR_NOT_PROVIDED;
        }

        final String headerWithoutTitle = header.substring(lastOpenParenIndex);
        final Matcher authorMatcher = AUTHOR_PATTERN.matcher(headerWithoutTitle);

        return authorMatcher.find()
                ? authorMatcher.group(1).trim()
                : AUTHOR_NOT_PROVIDED;
    }

}
