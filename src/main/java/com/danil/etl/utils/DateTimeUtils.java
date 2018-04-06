package com.danil.etl.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DateTimeUtils {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static LocalDateTime parseDateTime(String input) {
        final LocalDateTime resulLocalDateTime = LocalDateTime.parse(input, DATE_TIME_FORMATTER);
        return resulLocalDateTime;
    }
    public static LocalDate parseDate(String input) {
        final LocalDate resulLocalDate = LocalDate.parse(input, DATE_FORMATTER);
        return resulLocalDate;
    }
    public static LocalTime parseTime(String input) {
        final LocalTime resulLocalTime = LocalTime.parse(input, TIME_FORMATTER);
        return resulLocalTime;
    }
    public static LocalDateTime fromTimestampMilis(Long timestampSeconds){
        final LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampSeconds * 1000), TimeZone.getTimeZone("UTC").toZoneId());
        return localDateTime;
    }
}
