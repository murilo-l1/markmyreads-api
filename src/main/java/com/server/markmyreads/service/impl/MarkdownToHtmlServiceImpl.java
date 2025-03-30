package com.server.markmyreads.service.impl;

import com.server.markmyreads.domain.enumeration.NoteStyleEnum;
import com.server.markmyreads.service.MarkdownToHtmlService;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("Markdown2PdfService")
public class MarkdownToHtmlServiceImpl implements MarkdownToHtmlService {

    @Override
    public String convertMarkdownContentToHtml(@NonNull final String markdownContent, @NonNull final NoteStyleEnum style) {

        final Parser parser = Parser.builder().build();

        final HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();

        return style.getCss() + "<div>" + htmlRenderer.render(parser.parse(markdownContent)) + "</div>";
    }

}
