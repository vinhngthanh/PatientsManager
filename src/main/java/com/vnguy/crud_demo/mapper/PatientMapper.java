package com.vnguy.crud_demo.mapper;

import com.baeldung.openapi.model.PatientDto;
import com.baeldung.openapi.model.PatientRequest;
import com.vnguy.crud_demo.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {DateTimeMapper.class})
public interface PatientMapper {
    Patient toPatient(PatientRequest request);


    @Mapping(target="createdAt", source="createdAt", qualifiedByName = "toOffsetDateTime")
    @Mapping(target="updatedAt", source="updatedAt", qualifiedByName = "toOffsetDateTime")
    PatientDto toPatientDto(Patient patient);
}
