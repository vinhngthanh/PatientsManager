package com.vnguy.crud_demo.service;

import com.baeldung.openapi.model.Pageable;
import com.baeldung.openapi.model.PatientCriteria;
import com.baeldung.openapi.model.PatientRequest;
import com.baeldung.openapi.model.PatientDto;
import com.vnguy.crud_demo.mapper.PatientMapper;
import com.vnguy.crud_demo.model.Patient;
import com.vnguy.crud_demo.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements  PatientService{

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+\\d{1,3}-\\d{3}-\\d{3}-\\d{4}$");

    @Override
    public Page<PatientDto> getPatients(Pageable pageable, PatientCriteria criteria) {
        PageRequest sortedPageable = PageRequest.of(pageable.getPage(), pageable.getSize(), Sort.by("patientId").ascending());
        Specification<Patient> specification = buildSpecification(criteria);
        Page<Patient> patientPage = patientRepository.findAll(specification, sortedPageable);
        return patientPage.map(patientMapper::toPatientDto);
    }

    @Override
    public PatientDto getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient == null) {
            throw new NoSuchElementException("Patient not found with id: " + id);
        }
        return patientMapper.toPatientDto(patient);
    }

    @Override
    public PatientDto createPatient(PatientRequest patientDto) {
        validateInfo(patientDto.getEmail(), patientDto.getPhoneNumber());
        Patient patient = patientMapper.toPatient(patientDto);
        Patient savedPatient = patientRepository.save(patient);
        return patientMapper.toPatientDto(savedPatient);
    }

    @Override
    public PatientDto updatePatient(Long id, PatientRequest patientDto) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            throw new NoSuchElementException("Patient not found with id: " + id);
        }
        update(patient, patientDto);
        return patientMapper.toPatientDto(patientRepository.save(patient));
    }

    private void update(Patient patient, PatientRequest patientDto) {
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

    private void validateInfo(String email, String phoneNumber) {
        if (checkEmail(email)) {
            throw new IllegalArgumentException("Email is already in use: " + email);
        }
        if (validatePhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format: " + phoneNumber);
        }
        if (checkPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Phone number is already in use: " + phoneNumber);
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

    private Specification<Patient> buildSpecification(PatientCriteria criteria) {
        return Specification.where(hasId(criteria.getId()))
                .and(hasName(criteria.getName()))
                .and(hasGender(criteria.getGender()))
                .and(hasAge(criteria.getAge()))
                .and(hasEmail(criteria.getEmail()))
                .and(hasPhoneNumber(criteria.getPhoneNumber()));
    }

    private Specification<Patient> hasId(Long id) {
        return (root, query, criteriaBuilder) ->
                (id == null) ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("patientId"), id);
    }

    private Specification<Patient> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                (name == null || name.isEmpty()) ? criteriaBuilder.conjunction() : criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    private Specification<Patient> hasGender(String gender) {
        return (root, query, criteriaBuilder) ->
                (gender == null || gender.isEmpty()) ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("gender"), gender);
    }

    private Specification<Patient> hasAge(Integer age) {
        return (root, query, criteriaBuilder) ->
                (age == null) ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("age"), age);
    }

    private Specification<Patient> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                (email == null || email.isEmpty()) ? criteriaBuilder.conjunction() : criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    private Specification<Patient> hasPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) ->
                (phoneNumber == null || phoneNumber.isEmpty()) ? criteriaBuilder.conjunction() : criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), "%" + phoneNumber.toLowerCase() + "%");
    }
}
