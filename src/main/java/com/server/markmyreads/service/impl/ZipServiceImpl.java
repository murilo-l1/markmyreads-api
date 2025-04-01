package com.server.markmyreads.service.impl;

import com.server.markmyreads.domain.enumeration.NoteStyleEnum;
import com.server.markmyreads.domain.model.MarkMyReadsFile;
import com.server.markmyreads.handler.exception.ZipProcessingErrorException;
import com.server.markmyreads.service.HtmlToPdfService;
import com.server.markmyreads.service.MarkdownToHtmlService;
import com.server.markmyreads.service.ZipService;
import com.server.markmyreads.util.StringSanitizerUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service("ZipService")
@RequiredArgsConstructor
public class ZipServiceImpl implements ZipService {

    private final MarkdownToHtmlService toHtmlService;
    private final HtmlToPdfService toPdfService;

    private static final String MARKDOWN_EXTENSION = ".md";
    private static final String PDF_EXTENSION = ".pdf";
    private static final String FOLDER_PREFIX = "markmyreads/";

    @Override
    public byte[] zipMarkdowns(@NonNull final List<MarkMyReadsFile> files) {
        try(final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            final ZipOutputStream zipOut = new ZipOutputStream(byteOut)) {

            for (final MarkMyReadsFile file : files) {

                final String fileName = StringSanitizerUtil.sanitizeFileName(file.filename()) + MARKDOWN_EXTENSION;
                final ZipEntry entry = new ZipEntry(FOLDER_PREFIX + fileName);
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

    @Override
    public byte[] zipPdfs(@NonNull final List<MarkMyReadsFile> files, @NonNull final NoteStyleEnum pdfStyle) {
        try(final ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            final ZipOutputStream zipOut = new ZipOutputStream(byteOut)) {

            for (final MarkMyReadsFile file : files) {

                final String html = toHtmlService.convertMarkdownContentToHtml(file.content(), pdfStyle);
                final byte[] pdf = toPdfService.toPdfBytes(html);
                final String fileName = StringSanitizerUtil.sanitizeFileName(file.filename()) + PDF_EXTENSION;

                final ZipEntry entry = new ZipEntry(FOLDER_PREFIX + fileName);
                zipOut.putNextEntry(entry);

                zipOut.write(pdf);
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
