package com.vnguy.crud_demo.mapper;

import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DateTimeMapper {

    @Named("toLocalDate")
    default LocalDate toLocalDate(final @Nullable OffsetDateTime offsetDateTime) {
        return Optional.ofNullable(offsetDateTime)
                .map(OffsetDateTime::toLocalDate)
                .orElse(null);
    }


    @Named("toLocalDateTime")
    default LocalDateTime toLocalDateTime(final @Nullable OffsetDateTime offsetDateTime) {
        return Optional.ofNullable(offsetDateTime)
                .map(OffsetDateTime::toLocalDateTime)
                .orElse(null);
    }

    @Named("toOffsetDateTime")
    default OffsetDateTime toOffsetDateTime(final @Nullable LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime)
                .map(time -> time.atZone(ZoneOffset.systemDefault()))
                .map(ZonedDateTime::toOffsetDateTime)
                .orElse(null);
    }
}
