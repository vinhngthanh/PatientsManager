package com.vnguy.crud_demo.controller;

import com.vnguy.crud_demo.dto.PatientDto;
import com.vnguy.crud_demo.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "Get all patients")
    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getPatients());
    }

    @Operation(summary = "Get a patient by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(
            @Parameter(description = "Patient ID") @PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @Operation(summary = "Create a new patient")
    @PostMapping
    public ResponseEntity<PatientDto> createPatient(
            @Parameter(description = "Patient data") @RequestBody PatientDto patientDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(patientDto));
    }

    @Operation(summary = "Update an existing patient")
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(
            @Parameter(description = "Patient ID") @PathVariable Long id,
            @Parameter(description = "Updated patient data") @RequestBody PatientDto patientDto) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientDto));
    }

    @Operation(summary = "Delete a patient by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(
            @Parameter(description = "Patient ID") @PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
