package com.server.markmyreads.domain.component;

import com.server.markmyreads.domain.dto.ParsedClippingDto;
import com.server.markmyreads.util.DateParserUtil;
import com.server.markmyreads.util.StringSanitizerUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.server.markmyreads.domain.constant.ClippingsConstants.AUTHOR_NOT_PROVIDED;

@Component
@Slf4j
public class ClippingBlockParser {

    private static final Pattern AUTHOR_PATTERN = Pattern.compile("\\(([^)]+)\\)");

    public ParsedClippingDto parse(String rawBlock) {
        try {
            final String[] lines = rawBlock.split("\n", 4);
            if (lines.length < 4) {
                return null;
            }

            final String titleAndAuthor = lines[0].trim();
            final String metadata = lines[1].trim();
            final String content = lines[3].trim();

            final Matcher matcher = AUTHOR_PATTERN.matcher(titleAndAuthor);
            if (StringUtils.isBlank(content.trim()) || !matcher.find()) {
                return null;
            }

            return new ParsedClippingDto(
                    extractTitleFromLine(titleAndAuthor),
                    extractAuthorFromLine(titleAndAuthor),
                    content,
                    Objects.requireNonNull(DateParserUtil.parseToLocalDate(metadata)).date());
        }
        catch (Exception e) {
            log.error("error parsing note: {}", e.getMessage());
            return null;
        }
    }

    private String extractTitleFromLine(final String line) {
        if (StringUtils.isBlank(line)) {
            return "";
        }

        int lastOpenParenIndex = line.lastIndexOf("(");
        if (lastOpenParenIndex == -1) {
            return line.trim();
        }

        return StringSanitizerUtil.sanitizeTitle(line.substring(0, lastOpenParenIndex)).trim();
    }
    
    private String extractAuthorFromLine(final String line) {
        if (StringUtils.isBlank(line)) {
            return AUTHOR_NOT_PROVIDED;
        }

        int lastOpenParenIndex = line.lastIndexOf("(");
        if (lastOpenParenIndex == -1) {
            return AUTHOR_NOT_PROVIDED;
        }

        final String lineWithoutTitle = line.substring(lastOpenParenIndex);
        final Matcher authorMatcher = AUTHOR_PATTERN.matcher(lineWithoutTitle);

        return authorMatcher.find()
                ? authorMatcher.group(1).trim()
                : AUTHOR_NOT_PROVIDED;
    }

}
