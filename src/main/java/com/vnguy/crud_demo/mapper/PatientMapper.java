package com.vnguy.crud_demo.mapper;

import com.vnguy.crud_demo.dto.PatientDto;
import com.vnguy.crud_demo.model.Patient;
import org.mapstruct.MappingTarget;

import java.util.List;

public class PatientMapper {
    public static PatientDto toPatientDto(Patient patient) {
        return new PatientDto(patient.getPatientId(), patient.getName(), patient.getGender()
                , patient.getAge(), patient.getEmail(), patient.getPhoneNumber(), patient.getCreatedAt(),
                patient.getUpdatedAt());
    }

    public static List<PatientDto> toPatientDtos(List<Patient> patients) {
        return patients.stream().map(PatientMapper::toPatientDto).toList();
    }

    public static Patient convertToEntity(PatientDto patientDto) {
        return new Patient(patientDto.getPatientId(), patientDto.getName(), patientDto.getGender(), patientDto.getAge()
                , patientDto.getEmail(), patientDto.getPhoneNumber()
                , patientDto.getCreatedAt(), patientDto.getUpdatedAt());
    }
}
