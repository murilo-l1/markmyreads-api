package com.server.markmyreads.usecase.zip.impl;

import com.server.markmyreads.domain.dto.ClippingsContext;
import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.domain.model.KindleNote;
import com.server.markmyreads.domain.model.MarkMyReadsFile;
import com.server.markmyreads.handler.exception.ZipProcessingErrorException;
import com.server.markmyreads.service.ClippingsExtractorService;
import com.server.markmyreads.service.KindleNoteProviderService;
import com.server.markmyreads.service.MarkdownFormatterService;
import com.server.markmyreads.usecase.zip.ZipExport;
import com.server.markmyreads.util.StringSanitizerUtil;
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

    @Override
    public ResponseEntity<byte[]> convertToManyMarkdowns(@NonNull final MultipartFile file,
                                                         @NonNull final NoteSortType sort) {

        final ClippingsContext context = extractorService.extractClippingsBlocks(file);
        final List<KindleNote> notes = providerService.processAllNotesBySort(context, sort);
        final List<MarkMyReadsFile> markMyReadsFiles = formatterService.formatToManyMarkdowns(notes);

        byte[] zipContent = zipFiles(markMyReadsFiles);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("markmyreads.zip")
                .build());
        headers.setContentLength(zipContent.length);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(zipContent);
    }

    private byte[] zipFiles(@NonNull final List<MarkMyReadsFile> files) {
        try(final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            final ZipOutputStream zipOut = new ZipOutputStream(byteOut)) {

            final String folderName = "markmyreads/";

            for (final MarkMyReadsFile file : files) {

                final String fileName = StringSanitizerUtil.sanitizeFileName(file.filename()) + ".md";
                final ZipEntry entry = new ZipEntry(folderName + fileName);
                zipOut.putNextEntry(entry);

                final byte[] content = file.content().getBytes(StandardCharsets.UTF_8);
                zipOut.write(content);
                zipOut.closeEntry();
            }

            zipOut.close();
            return byteOut.toByteArray();
        }
        catch (IOException e) {
            throw new ZipProcessingErrorException();
        }
    }

}
