package com.server.markmyreads.controller.privat;

import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.usecase.validator.ClippingsValidator;
import com.server.markmyreads.usecase.markdown.MarkdownExport;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RestController("FileController")
@RequestMapping(value = "/api/clippings", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClippingsController {

    private final ClippingsValidator validatorService;
    private final MarkdownExport export;

    @PostMapping("/export")
    public ResponseEntity<byte[]> convert(@NonNull @NotNull @RequestParam("file") final MultipartFile file,
                                          @RequestParam(value = "sort", required = false, defaultValue = "DATE_DESC") final NoteSortType sort) {

        validatorService.validate(file);

        return export.convertToSingleMarkdown(file, sort);
    }

}
