package com.server.markmyreads.controller.privat;

import com.server.markmyreads.domain.enumeration.ExportOptionEnum;
import com.server.markmyreads.domain.enumeration.NoteSortType;
import com.server.markmyreads.domain.enumeration.NoteStyleEnum;
import com.server.markmyreads.usecase.pdf.PdfExport;
import com.server.markmyreads.usecase.validator.ClippingsValidator;
import com.server.markmyreads.usecase.markdown.MarkdownExport;
import com.server.markmyreads.usecase.zip.ZipExport;
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

    @PostMapping(value = "/export", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> export(@NonNull @NotNull @RequestParam("file") final MultipartFile file,
                                         @NonNull @NotNull @RequestParam("export") final ExportOptionEnum export,
                                         @NonNull @NotNull @RequestParam(name = "sort", required = false, defaultValue = "DATE_DESC") final NoteSortType sort,
                                         @NonNull @NotNull @RequestParam(name = "style", required = false, defaultValue = "CLASSIC") final NoteStyleEnum style) {

        validator.validate(file);

        return switch (export) {
            case MARKDOWN -> mardownExport.convertToSingleMarkdown(file, sort);

            case PDF -> pdfExport.convertToSinglePdf(file, sort, style);

            case ZIP_MARKDOWNS -> zipExport.convertToManyMarkdowns(file, sort);

            case ZIP_PDFS -> zipExport.convertToManyPdfs(file, sort, style);
        };
    }

}
