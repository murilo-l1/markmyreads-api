package com.server.markmyreads.usecase.markdown.impl;

import com.server.markmyreads.domain.dto.ClippingsContext;
import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.domain.model.KindleNote;
import com.server.markmyreads.domain.model.MarkMyReadsFile;
import com.server.markmyreads.service.ClippingsExtractorService;
import com.server.markmyreads.service.KindleNoteProviderService;
import com.server.markmyreads.service.MarkdownFormatterService;
import com.server.markmyreads.usecase.markdown.MarkdownExport;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service("MarkdownExport")
@RequiredArgsConstructor
public class MarkdownExportImpl implements MarkdownExport {

    private final ClippingsExtractorService extractorService;
    private final KindleNoteProviderService providerService;
    private final MarkdownFormatterService formatterService;

    @Override
    public ResponseEntity<byte[]> convertToSingleMarkdown(@NonNull final MultipartFile file, final NoteSortType sortCriteria) {

        final ClippingsContext context = extractorService.extractClippingsBlocks(file);
        final List<KindleNote> notes = providerService.processAllNotesBySort(context, sortCriteria);
        final MarkMyReadsFile outputFile = formatterService.formatToSingleMarkdown(notes);

        final byte[] fileBytes = outputFile.content().getBytes(StandardCharsets.UTF_8);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_MARKDOWN);
        headers.setContentDisposition(ContentDisposition.attachment()
                        .filename(outputFile.filename() + ".md")
                        .build());
        headers.setContentLength(fileBytes.length);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(fileBytes);
    }

}
