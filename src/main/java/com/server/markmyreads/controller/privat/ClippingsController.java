package com.server.markmyreads.controller.privat;

import com.server.markmyreads.service.ClippingsValidatorService;
import com.server.markmyreads.usecase.markdown.MarkdownExport;
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
    private final MarkdownExport export;

    @PostMapping("/export")
    public ResponseEntity<byte[]> convert(@NonNull @NotNull @RequestParam("file") final MultipartFile file) {

        validatorService.validate(file);

        return export.convertToSingleMarkdown(file, null);
    }

}
