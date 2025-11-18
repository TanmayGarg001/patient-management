package com.psbe.patientservicebe.services;

import com.psbe.patientservicebe.dtos.PatientRequestDTO;
import com.psbe.patientservicebe.dtos.PatientResponseDTO;
import com.psbe.patientservicebe.exceptions.EmailAlreadyExistsException;
import com.psbe.patientservicebe.exceptions.PatientNotFoundException;
import com.psbe.patientservicebe.mappers.PatientMapper;
import com.psbe.patientservicebe.models.Patient;
import com.psbe.patientservicebe.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {//returns list of all patients present in the database
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::parseToDTO).toList();
    }

    public PatientResponseDTO getPatientById(UUID patientId) {//returns a patient by its id
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient with id " + patientId + " not found!"));
        return PatientMapper.parseToDTO(patient);
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {//adds a new patient to the database
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Patient with email " + patientRequestDTO.getEmail() + " already exists!");
        }
        Patient patient = patientRepository.save(PatientMapper.parseToModel(patientRequestDTO));
        return PatientMapper.parseToDTO(patient);
    }

    public PatientResponseDTO updatePatientById(UUID patientId, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient with id " + patientId + " not found!"));
        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), patientId)) {
            throw new EmailAlreadyExistsException("Patient with email " + patientRequestDTO.getEmail() + " already exists!");
        }
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setName(patientRequestDTO.getName());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.parseToDTO(updatedPatient);
    }

    public void deletePatientById(UUID patientId) {
        if (patientRepository.existsById(patientId)) {
            patientRepository.deleteById(patientId);
        } else {
            throw new PatientNotFoundException("Patient with id " + patientId + " not found!");
        }
    }


}
