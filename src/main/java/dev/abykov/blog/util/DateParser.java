package dev.abykov.blog.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Utility class for parsing various date representations (ISO strings, LocalDate, java.util.Date).
 */
public final class DateParser {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    private DateParser() {
        // Utility class
    }

    /**
     * Parses a date from different possible types:
     * <ul>
     *     <li>{@link java.util.Date}</li>
     *     <li>{@link java.lang.String} — ISO or yyyy-MM-dd</li>
     * </ul>
     *
     * @param value input date
     * @return parsed {@link LocalDateTime}, or {@code null} if value is null or invalid
     */
    public static LocalDateTime parse(Object value) {
        if (value == null) {
            return null;
        }

        try {
            if (value instanceof Date date) {
                return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            }
            if (value instanceof String s) {
                return parseString(s);
            }
        } catch (Exception e) {
            System.err.println("Failed to parse date: " + value + " (" + e.getMessage() + ")");
        }
        return null;
    }

    private static LocalDateTime parseString(String s) {
        try {
            return LocalDateTime.parse(s, ISO_FORMATTER);
        } catch (Exception e1) {
            try {
                return LocalDate.parse(s).atStartOfDay();
            } catch (Exception e2) {
                throw new IllegalArgumentException("Invalid date format: " + s);
            }
        }
    }
}
