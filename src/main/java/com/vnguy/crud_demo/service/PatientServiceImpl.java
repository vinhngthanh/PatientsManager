package com.vnguy.crud_demo.service;

import com.vnguy.crud_demo.dto.PatientDto;
import com.vnguy.crud_demo.mapper.PatientMapper;
import com.vnguy.crud_demo.model.Patient;
import com.vnguy.crud_demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Patient not found with id: " + id));
        return PatientMapper.toPatientDto(patient);
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
        patient.setName(patientDto.getName());
        patient.setGender(patientDto.getGender());
        patient.setAge(patientDto.getAge());
        patient.setEmail(patientDto.getEmail());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        return PatientMapper.toPatientDto(patientRepository.save(patient));
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}
