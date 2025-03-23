package com.server.markmyreads.controller.privat;

import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.handler.exception.InvalidExportMethodException;
import com.server.markmyreads.usecase.validator.ClippingsValidator;
import com.server.markmyreads.usecase.markdown.MarkdownExport;
import com.server.markmyreads.usecase.zip.ZipExport;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RestController("FileController")
@RequestMapping(value = "/api/clippings", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClippingsController {

    private final ClippingsValidator validator;
    private final MarkdownExport mardownExport;
    private final ZipExport zipExport;

    @PostMapping("/export")
    public ResponseEntity<byte[]> convert(@NonNull @NotNull @RequestParam("file") final MultipartFile file,
                                          @NonNull @NotNull @RequestParam("export") final String export,
                                          @RequestParam(value = "sort", required = false, defaultValue = "DATE_DESC") final NoteSortType sort) {

        validator.validate(file);

        return switch (export) {
            case "MARKDOWN" -> mardownExport.convertToSingleMarkdown(file, sort);

            case "ZIP" -> zipExport.convertToManyMarkdowns(file, sort);

            default -> throw new InvalidExportMethodException();
        };
    }

}
