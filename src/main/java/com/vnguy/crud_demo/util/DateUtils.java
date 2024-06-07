package com.vnguy.crud_demo.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@UtilityClass
public class DateUtils {

    public static OffsetDateTime convertDateToOffsetDateTime(Date date) {
        Instant instant = date.toInstant();

        return instant.atZone(ZoneId.systemDefault()).toOffsetDateTime();
    }

    public static Date toDate(OffsetDateTime offsetDateTime){
        return Date.from(offsetDateTime.toInstant());
    }
    public static LocalDateTime toLocalDateTime(Date dateToConvert) {
        return Objects.nonNull(dateToConvert) ? dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime() : LocalDateTime.now();
    }

    public static String toFormat(LocalDate date, String format) {
        return Objects.isNull(date)
                ? StringUtils.EMPTY
                : date.format(DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime toTimezoneOf(String timezone, LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return null;
        }
        ZoneOffset zoneOffset = ZoneOffset.of(timezone);
        return dateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(zoneOffset).toLocalDateTime();
    }
}