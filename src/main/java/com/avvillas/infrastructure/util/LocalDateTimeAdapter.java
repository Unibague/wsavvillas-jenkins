package com.avvillas.infrastructure.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Adaptador para formatear los tipos de dato {@link LocalDateTime}
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public LocalDateTime unmarshal(String value) {
        return LocalDateTime.parse(value, formatter);
    }

    @Override
    public String marshal(LocalDateTime value) {
        return formatter.format(value);
    }
}
