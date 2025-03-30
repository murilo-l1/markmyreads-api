package com.server.markmyreads.controller.privat;

import com.server.markmyreads.domain.enumeration.ExportOptionEnum;
import com.server.markmyreads.domain.payload.NoteFormatOptionsPayload;
import com.server.markmyreads.usecase.pdf.PdfExport;
import com.server.markmyreads.usecase.validator.ClippingsValidator;
import com.server.markmyreads.usecase.markdown.MarkdownExport;
import com.server.markmyreads.usecase.zip.ZipExport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("ClippingsController")
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/clippings", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClippingsController {

    private final ClippingsValidator validator;
    private final MarkdownExport mardownExport;
    private final ZipExport zipExport;
    private final PdfExport pdfExport;

    @PostMapping("/export")
    public ResponseEntity<byte[]> convert(@NonNull @NotNull @RequestParam("file") final MultipartFile file,
                                          @NonNull @NotNull @RequestParam("export") final ExportOptionEnum export,
                                          @NonNull @Valid final NoteFormatOptionsPayload payload) {

        validator.validate(file);

        return switch (export) {
            case MARKDOWN -> mardownExport.convertToSingleMarkdown(file, payload.getSort());

            case ZIP -> zipExport.convertToManyMarkdowns(file, payload.getSort());

            case PDF -> pdfExport.convertToSinglePdf(file, null);
        };
    }

}
