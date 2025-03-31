package com.server.markmyreads.usecase.pdf.impl;

import com.server.markmyreads.domain.dto.ClippingsContext;
import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.domain.enumeration.NoteStyleEnum;
import com.server.markmyreads.domain.model.KindleNote;
import com.server.markmyreads.domain.model.MarkMyReadsFile;
import com.server.markmyreads.service.*;
import com.server.markmyreads.usecase.pdf.PdfExport;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service("PdfExport")
@RequiredArgsConstructor
public class PdfExportImpl implements PdfExport {

    private final ClippingsExtractorService extractor;
    private final KindleNoteProviderService provider;
    private final MarkdownFormatterService formatterService;
    private final MarkdownToHtmlService toHtmlService;
    private final HtmlToPdfService toPdfService;

    @Override
    public ResponseEntity<byte[]> convertToSinglePdf(@NonNull final MultipartFile file,
                                                     @NonNull final NoteSortType sort,
                                                     @NonNull final NoteStyleEnum style) {
        final ClippingsContext context = extractor.extractClippingsBlocks(file);
        final List<KindleNote> notes = provider.processAllNotesBySort(context, sort);
        final MarkMyReadsFile markMyReadsFile = formatterService.formatToSingleMarkdown(notes);

        final String htmlContent = toHtmlService.convertMarkdownContentToHtml(markMyReadsFile.content(), style);

        final byte[] pdfBytes = toPdfService.toPdfBytes(htmlContent);

        final HttpHeaders headers = createHeaders((long) pdfBytes.length);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdfBytes);
    }

    private HttpHeaders createHeaders(@NonNull final Long bytesLenght) {
        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("markmyreads.pdf")
                .build());
        headers.setContentLength(bytesLenght);

        return headers;
    }
}
