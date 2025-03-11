package com.server.markmyreads.util;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSanitizerUtil {

    private StringSanitizerUtil(){}

    public static String sanitizeFileName(final String original) {

        if (StringUtils.isBlank(original)) {
            return "unnamed";
        }

        String cleanedup = cleanupIsbnTitle(original);

        cleanedup = removeIllegalFileCharacters(cleanedup);
        cleanedup = removeCtrlCharacter(cleanedup);
        cleanedup = removeLeadingAndTrailingDots(cleanedup);

        return !cleanedup.trim().isEmpty()
                ? cleanedup
                : "unnamed";
    }

    public static String sanitizeTitle(final String original) {

        if (StringUtils.isBlank(original)) {
            return "unnamed";
        }

        String cleanedup = cleanupIsbnTitle(original);
        cleanedup = removeCtrlCharacter(cleanedup);

        return !cleanedup.trim().isEmpty()
                ? cleanedup
                : "unnamed";
    }

    private static String cleanupIsbnTitle(String title) {

        if (title.length() < 80) {
            return title;
        }

        // some kindle files have an ISBN on title, placed right after the title itself
        if (title.contains("-") && title.matches(".*\\d{7,}.*")) {

            final String isbnPattern = "\\d+-\\d+-\\d+-\\d+|\\d{10,13}";

            int firstIsbnIdx = -1;
            final Matcher matcher = Pattern.compile(isbnPattern).matcher(title);

            if (matcher.find()) {
                firstIsbnIdx = matcher.start();
            }

            if (firstIsbnIdx > 0) {
                int lastHyphenBeforeIsbn = title.substring(0, firstIsbnIdx).lastIndexOf("-");

                if (lastHyphenBeforeIsbn > 0) {
                    return title.substring(0, lastHyphenBeforeIsbn).trim();
                }
            }

            title = title.replaceAll(isbnPattern, "");

            title = title.endsWith("-") ? title.substring(0, title.length() - 1) : title;
        }

        return title.trim();
    }

    private static String removeIllegalFileCharacters(@NonNull @NotBlank final String raw) {
        return raw.replaceAll("[\\\\/:*?\"<>|]", "-");
    }

    private static String removeCtrlCharacter(@NonNull @NotBlank final String raw) {
        return raw.replaceAll("\\p{Cntrl}", "");
    }

    private static String removeLeadingAndTrailingDots(@NonNull @NotBlank final String raw) {
        return raw.contains(".")
                ? raw.trim().replaceAll("^\\.+|\\.+$", "")
                : raw;
    }

}
