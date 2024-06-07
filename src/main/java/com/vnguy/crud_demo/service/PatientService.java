package com.vnguy.crud_demo.service;

import com.baeldung.openapi.model.Pageable;
import com.baeldung.openapi.model.PatientCriteria;
import com.baeldung.openapi.model.PatientRequest;
import com.baeldung.openapi.model.PatientDto;
import org.springframework.data.domain.Page;

public interface PatientService {
    Page<PatientDto> getPatients(Pageable pageable, PatientCriteria criteria);
    PatientDto getPatientById(Long id);
    PatientDto createPatient(PatientRequest patientDto);
    PatientDto updatePatient(Long id, PatientRequest patientDto);
    void deletePatient(Long id);
}
