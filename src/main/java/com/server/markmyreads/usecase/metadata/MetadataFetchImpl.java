package com.server.markmyreads.usecase.metadata;

import com.server.markmyreads.domain.dto.ClippingsContext;
import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.domain.model.KindleNote;
import com.server.markmyreads.service.ClippingsExtractorService;
import com.server.markmyreads.service.KindleNoteProviderService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetadataFetchImpl implements MetadataFetch {

    private final ClippingsExtractorService extractor;
    private final KindleNoteProviderService provider;

    @Override
    public ResponseEntity<List<String>> fetch(@NotNull final MultipartFile clippings) {
        final ClippingsContext context = extractor.extractClippingsBlocks(clippings);
        final List<KindleNote> notes = provider.processAllNotesBySort(context, NoteSortType.NOTES_COUNT);

        final List<String> metadata = notes.stream().map(note -> {
            return new String("a");
        }).toList();

        return ResponseEntity.ok(metadata);
    }
}
