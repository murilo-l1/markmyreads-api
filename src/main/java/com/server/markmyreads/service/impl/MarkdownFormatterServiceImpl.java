package com.server.markmyreads.service.impl;

import com.server.markmyreads.domain.constant.MarkdownResultConstants;
import com.server.markmyreads.domain.model.KindleNote;
import com.server.markmyreads.domain.model.MarkMyReadsFile;
import com.server.markmyreads.service.MarkdownFormatterService;
import com.server.markmyreads.util.StringSanitizerUtil;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.server.markmyreads.domain.constant.MarkdownResultConstants.DOUBLE_LINE_BREAK;

@Slf4j
@Service("MarkdownFormatterService")
public class MarkdownFormatterServiceImpl implements MarkdownFormatterService {

    @Override
    public MarkMyReadsFile formatToSingleMarkdown(@NonNull final List<KindleNote> notes) {
        final StringBuilder markdownContent = new StringBuilder();

        for (final KindleNote note : notes) {
            markdownContent.append(formatSingleNote(note));
            markdownContent.append(MarkdownResultConstants.NOTE_DELIMITER);
        }

        return new MarkMyReadsFile(MarkdownResultConstants.RESULT_FILE_NAME, markdownContent.toString());
    }

    private String formatSingleNote (final KindleNote note) {

        return MarkdownResultConstants.HEADER_TAG +
                StringSanitizerUtil.sanitizeTitle(note.getTitle()) +
                " - " +
                note.getAuthor() +
                DOUBLE_LINE_BREAK +
                formatSingleNoteContent(note);
    }

    private String formatSingleNoteContent(final KindleNote note) {
        final StringBuilder content = new StringBuilder();

        log.info("came here with lastReadAt as: {}", note.getLastReadAt());

        if (note.getLastReadAt() != null) {
            content.append("Last read at: ")
                    .append(note.getLastReadAt().date())
                    .append(DOUBLE_LINE_BREAK);
        }

        if (note.getNotes() != null && !note.getNotes().isEmpty()) {
            for (final String individualNote : note.getNotes()) {
                content.append(MarkdownResultConstants.BULLET_POINT_TAG)
                        .append(individualNote)
                        .append(DOUBLE_LINE_BREAK);
            }
            content.append("\n");
        }

        return content.toString();
    }

}
