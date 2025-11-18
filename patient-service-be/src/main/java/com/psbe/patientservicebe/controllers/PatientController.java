package com.psbe.patientservicebe.controllers;

import com.psbe.patientservicebe.dtos.PatientRequestDTO;
import com.psbe.patientservicebe.dtos.PatientResponseDTO;
import com.psbe.patientservicebe.dtos.validators.CreatePatientValidationGroup;
import com.psbe.patientservicebe.services.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient Controller", description = "APIs for managing patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/v1")
    @Operation(summary = "Get all patients", description = "Retrieve a list of all patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() {
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);// return 200 OK with patients in the body
    }

    @GetMapping("v1/{patientId}")
    @Operation(summary = "Get patient by id", description = "Retrieve a patient by their unique ID")
    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable("patientId") UUID patientId) {
        PatientResponseDTO patient = patientService.getPatientById(patientId);
        return ResponseEntity.ok().body(patient); // return 200 OK with patient in the body
    }

    @PostMapping("/v1")
    @Operation(summary = "Create a new patient", description = "Create a new patient with the provided details")
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patient = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patient); // return 200 OK with saved patient in the body
    }

    @PutMapping("/v1/{patientId}")
    @Operation(summary = "Update patient by id", description = "Update an existing patient's details by their unique ID")
    public ResponseEntity<PatientResponseDTO> updatePatientById(@PathVariable UUID patientId, @Validated(Default.class) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO updatedPatient = patientService.updatePatientById(patientId, patientRequestDTO);
        return ResponseEntity.ok().body(updatedPatient); // return 200 OK with updated patient in the body
    }

    @DeleteMapping("/v1/{patientId}")
    @Operation(summary = "Delete patient by id", description = "Delete a patient by their unique ID")
    public ResponseEntity<Void> deletePatientById(@PathVariable UUID patientId) {
        patientService.deletePatientById(patientId);
        return ResponseEntity.noContent().build(); // return 204 No Content
    }

}
