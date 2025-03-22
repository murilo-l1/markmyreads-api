package com.server.markmyreads.usecase.zip.impl;

import com.server.markmyreads.domain.dto.ClippingsContext;
import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.domain.model.KindleNote;
import com.server.markmyreads.domain.model.MarkMyReadsFile;
import com.server.markmyreads.service.ClippingsExtractorService;
import com.server.markmyreads.service.KindleNoteProviderService;
import com.server.markmyreads.service.MarkdownFormatterService;
import com.server.markmyreads.usecase.zip.ZipExport;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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




        return null;
    }
}
