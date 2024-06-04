package com.vnguy.crud_demo.service;

import com.vnguy.crud_demo.dto.PatientDto;
import com.vnguy.crud_demo.mapper.PatientMapper;
import com.vnguy.crud_demo.model.Patient;
import com.vnguy.crud_demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements  PatientService{

    private final PatientRepository patientRepository;

    @Override
    public List<PatientDto> getPatients() {
        return PatientMapper.toPatientDtos(patientRepository.findAll());
    }

    @Override
    public PatientDto getPatientById(Long id) {
        return PatientMapper.toPatientDto(Objects.requireNonNull(patientRepository.findById(id).orElse(null)));
    }

    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        return PatientMapper.toPatientDto(patientRepository.save(PatientMapper.convertToEntity(patientDto)));
    }

    @Override
    public PatientDto updatePatient(Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            return null;
        }
        return PatientMapper.toPatientDto(patientRepository
                .save(PatientMapper.convertToEntity(patientDto)));
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
