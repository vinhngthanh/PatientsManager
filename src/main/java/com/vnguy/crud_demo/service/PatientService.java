package com.vnguy.crud_demo.service;

import com.vnguy.crud_demo.dto.PatientDto;

import java.util.List;

public interface PatientService {
    List<PatientDto> getPatients();
    PatientDto getPatientById(Long id);
    PatientDto createPatient(PatientDto patientDto);
    PatientDto updatePatient(Long id, PatientDto patientDto);
    void deletePatient(Long id);
}
