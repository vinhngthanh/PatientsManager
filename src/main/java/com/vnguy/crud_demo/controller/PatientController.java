package com.vnguy.crud_demo.controller;

import com.vnguy.crud_demo.model.Patient;
import com.vnguy.crud_demo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return PatientService.findAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return PatientService.findPatientById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return PatientService.savePatient(patient);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Patient> updateUser(@PathVariable Long id, @RequestBody Patient patient) {
//        return PatientService.findPatientById(id)
//                .map(existingPatient -> {
//                    return ResponseEntity.ok(patientService.updatePatient(patient));
//                })
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        return PatientService.findPatientById(id)
                .map(user -> {
                    PatientService.deletePatient(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
