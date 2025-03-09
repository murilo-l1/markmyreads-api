package com.server.markmyreads.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class DateParserUtil {

    private static final Pattern fullDateFormatRgx = Pattern.compile("(?i)(Adicionado:|Added:)\\s*(.+)");

    private DateParserUtil() {}

    public static LocalDate parseToLocalDate(final String lineWithExtendedDate) {

        final boolean isPortuguese = lineWithExtendedDate.matches(".*\\b(domingo|segunda-feira|terça-feira|quarta-feira|quinta-feira|sexta-feira|sábado)\\b.*");

        final Matcher matcher = fullDateFormatRgx.matcher(lineWithExtendedDate);

        if (!matcher.find()) {
            return null;
        }

        final String datePart = matcher.group(2).trim();

        final DateTimeFormatter ptFormatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy HH:mm:ss", Locale.of("pt", "BR"));
        final DateTimeFormatter enFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy HH:mm:ss", Locale.ENGLISH);

        try {
            if (isPortuguese) {
                return LocalDate.parse(datePart, ptFormatter);
            }
            else {
                return LocalDate.parse(datePart, enFormatter);
            }
        }
        catch (Exception e) {
            log.error("Could not extract date, exception: {}", e.getMessage());
            return null;
        }
    }

}
