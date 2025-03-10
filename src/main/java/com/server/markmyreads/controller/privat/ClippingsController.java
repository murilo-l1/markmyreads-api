package com.server.markmyreads.controller.privat;

import com.server.markmyreads.domain.enumeration.NotesSortCriteriaEnum;
import com.server.markmyreads.domain.model.KindleNote;
import com.server.markmyreads.service.ClippingsValidatorService;
import com.server.markmyreads.service.KindleNoteProviderService;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController("FileController")
@RequestMapping(value = "/api/clippings", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClippingsController {

    private final ClippingsValidatorService validatorService;
    private final KindleNoteProviderService providerService;

    @PostMapping("/export")
    public ResponseEntity<String> convert(@NonNull @NotNull @RequestParam("file") final MultipartFile file) {

        validatorService.validate(file);

        final List<KindleNote> notes = providerService.processAllNotesBySort(n, NotesSortCriteriaEnum.BY_DATE_DESC);

        return ResponseEntity.ok(notes.getFirst().toString());
    }

}
