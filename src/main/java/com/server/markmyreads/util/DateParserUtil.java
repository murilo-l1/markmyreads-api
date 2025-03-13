package com.server.markmyreads.util;

import com.server.markmyreads.domain.dto.NoteDateContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public final class DateParserUtil {

    private static final Pattern fullDateFormatRgx = Pattern.compile("(?i)(Adicionado:|Added on)\\s*(.+)");

    private DateParserUtil() {}

    public static NoteDateContext parseToLocalDate(final String lineWithExtendedDate) {

        final boolean isPortuguese = lineWithExtendedDate.matches(".*\\b(domingo|segunda-feira|terça-feira|quarta-feira|quinta-feira|sexta-feira|sábado)\\b.*");

        final Matcher matcher = fullDateFormatRgx.matcher(lineWithExtendedDate);

        if (!matcher.find()) {
            return null;
        }

        final String datePart = matcher.group(2).trim();

        final DateTimeFormatter ptFormatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM 'de' yyyy HH:mm:ss", Locale.of("pt", "BR"));
        final DateTimeFormatter enFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy HH:mm:ss", Locale.ENGLISH);

        try {
            if (isPortuguese) {
                return new NoteDateContext(LocalDate.parse(datePart, ptFormatter), Locale.of("pt", "BR"));
            }
            else {
                return new NoteDateContext(LocalDate.parse(datePart, enFormatter), Locale.ENGLISH);
            }
        }
        catch (Exception e) {
            log.error("Could not extract date, exception: {}", e.getMessage());
            return null;
        }
    }

    public static String toExtendedDateForm(final NoteDateContext dateContext) {
        return DateTimeFormatter.ofPattern("d MMM yyyy", dateContext.locale()).format(dateContext.date());
    }

}
