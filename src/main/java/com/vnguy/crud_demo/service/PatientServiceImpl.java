package com.vnguy.crud_demo.service;

import com.vnguy.crud_demo.dto.PatientDto;
import com.vnguy.crud_demo.mapper.PatientMapper;
import com.vnguy.crud_demo.model.Patient;
import com.vnguy.crud_demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements  PatientService{

    private final PatientRepository patientRepository;
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+\\d{1,3}\\d{10}$");

    @Override
    public List<PatientDto> getPatients() {
        return PatientMapper.toPatientDtos(patientRepository.findAll());
    }

    @Override
    public PatientDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient == null) {
            throw new NoSuchElementException("Patient not found with id: " + id);
        }
        return PatientMapper.toPatientDto(patient);
    }

    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        validateInfo(patientDto);
        Patient patient = PatientMapper.convertToEntity(patientDto);
        Patient savedPatient = patientRepository.save(patient);
        return PatientMapper.toPatientDto(savedPatient);
    }

    @Override
    public PatientDto updatePatient(Long id, PatientDto patientDto) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            throw new NoSuchElementException("Patient not found with id: " + id);
        }
        update(patient, patientDto);
        return PatientMapper.toPatientDto(patientRepository.save(patient));
    }

    private void update(Patient patient, PatientDto patientDto) {
        if (validatePhoneNumber(patientDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number format: " + patientDto.getPhoneNumber());
        }
        if(!Objects.equals(patient.getPhoneNumber(), patientDto.getPhoneNumber())){
            if (checkPhoneNumber(patientDto.getPhoneNumber())) {
                throw new IllegalArgumentException("Phone number is already in use: " + patientDto.getPhoneNumber());
            }
        }
        if(!Objects.equals(patient.getEmail(), patientDto.getEmail())){
            if (checkEmail(patientDto.getEmail())) {
                throw new IllegalArgumentException("Email is already in use: " + patientDto.getEmail());
            }
        }
        patient.setName(patientDto.getName());
        patient.setGender(patientDto.getGender());
        patient.setAge(patientDto.getAge());
        patient.setEmail(patientDto.getEmail());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
    }

    private void validateInfo(PatientDto patientDto) {
        if (checkEmail(patientDto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use: " + patientDto.getEmail());
        }
        if (validatePhoneNumber(patientDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Invalid phone number format: " + patientDto.getPhoneNumber());
        }
        if (checkPhoneNumber(patientDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number is already in use: " + patientDto.getPhoneNumber());
        }
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        return !PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        return patientRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    private boolean checkEmail(String email) {
        return email != null && patientRepository.findByEmail(email).isPresent();
    }
}
