package com.psbe.patientservicebe.mappers;

import com.psbe.patientservicebe.dtos.PatientRequestDTO;
import com.psbe.patientservicebe.dtos.PatientResponseDTO;
import com.psbe.patientservicebe.models.Patient;

import java.time.LocalDate;

public class PatientMapper {


    public static PatientResponseDTO parseToDTO(Patient patient) {//Converts Patient entity to PatientResponseDTO to send data back to the client
        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId().toString());
        patientResponseDTO.setName(patient.getName());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfBirth(patient.getDateOfBirth().toString());
        return patientResponseDTO;
    }

    public static Patient parseToModel(PatientRequestDTO patientRequestDTO) {//Converts PatientRequestDTO to Patient entity to save data to the database
        Patient patient = new Patient();
        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate()));
        return patient;
    }


}
