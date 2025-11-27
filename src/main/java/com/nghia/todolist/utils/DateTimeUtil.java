package com.nghia.todolist.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final String baseDateTime = "dd/MM/YYYY hh:mm:ss";
    private static final DateTimeFormatter fullDateTime = DateTimeFormatter.ofPattern(baseDateTime);
    private static final DateTimeFormatter ISO_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, fullDateTime);
    }

    /**
     * Parse chuỗi ngày giờ sang LocalDateTime
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, ISO_DATE_TIME);
    }
}
