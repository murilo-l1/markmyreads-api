package com.server.markmyreads.usecase.zip.impl;

import com.server.markmyreads.domain.dto.ClippingsContext;
import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.domain.enumeration.NoteStyleEnum;
import com.server.markmyreads.domain.model.KindleNote;
import com.server.markmyreads.domain.model.MarkMyReadsFile;
import com.server.markmyreads.handler.exception.ZipProcessingErrorException;
import com.server.markmyreads.service.ClippingsExtractorService;
import com.server.markmyreads.service.KindleNoteProviderService;
import com.server.markmyreads.service.MarkdownFormatterService;
import com.server.markmyreads.service.ZipService;
import com.server.markmyreads.usecase.zip.ZipExport;
import com.server.markmyreads.util.StringSanitizerUtil;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service("ZipExport")
@RequiredArgsConstructor
public class ZipExportImpl implements ZipExport {

    private final ClippingsExtractorService extractorService;
    private final KindleNoteProviderService providerService;
    private final MarkdownFormatterService formatterService;
    private final ZipService zipService;

    @Override
    public ResponseEntity<byte[]> convertToManyMarkdowns(@NonNull final MultipartFile file,
                                                         @NonNull final NoteSortType sort) {

        final ClippingsContext context = extractorService.extractClippingsBlocks(file);
        final List<KindleNote> notes = providerService.processAllNotesBySort(context, sort);
        final List<MarkMyReadsFile> markMyReadsFiles = formatterService.formatToManyMarkdowns(notes);

        final byte[] zipContent = zipService.zipMarkdowns(markMyReadsFiles);

        return ResponseEntity
                .ok()
                .headers(createHeaders(zipContent.length))
                .body(zipContent);
    }

    @Override
    public ResponseEntity<byte[]> convertToManyPdfs(@NonNull final MultipartFile file, @NonNull final NoteSortType sort, @NonNull final NoteStyleEnum style) {

        final ClippingsContext context = extractorService.extractClippingsBlocks(file);
        final List<KindleNote> notes = providerService.processAllNotesBySort(context, sort);
        final List<MarkMyReadsFile> files = formatterService.formatToManyMarkdowns(notes);

        final byte[] zippedPdfs = zipService.zipPdfs(files, style);

        return ResponseEntity
                .ok()
                .headers(createHeaders(zippedPdfs.length))
                .body(zippedPdfs);
    }

    private HttpHeaders createHeaders(final long length){
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("markmyreads.zip")
                .build());
        headers.setContentLength(length);

        return headers;
    }

}
