package com.vnguy.crud_demo.rest;

import com.baeldung.openapi.api.PatientsApiDelegate;
import com.baeldung.openapi.model.*;
import com.vnguy.crud_demo.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientsApiDelegateImpl implements PatientsApiDelegate {

    private final PatientService patientService;

    @Override
    public ResponseEntity<PatientDto> createPatients(PatientRequest patientRequest) {
        return ResponseEntity.ok(patientService.createPatient(patientRequest));
    }

    @Override
    public ResponseEntity<Void> deletePatient(Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PageDto> getAllPatients(PatientCriteria criteria, Pageable pageable) {
        System.out.println(pageable.getPage() + " " + pageable.getSize());
        Page<PatientDto> page = patientService.getPatients(pageable, criteria);
        PageDto pageDto = new PageDto()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages());
        return ResponseEntity.ok(pageDto);
    }

    @Override
    public ResponseEntity<PatientDto> getPatientById(Long id) {
        PatientDto patientDto = patientService.getPatientById(id);
        return ResponseEntity.ok(patientDto);
    }

    @Override
    public ResponseEntity<PatientDto> updatePatient(Long id, PatientRequest patientRequest) {
        PatientDto patientDto = patientService.updatePatient(id, patientRequest);
        return ResponseEntity.ok(patientDto);
    }
}
