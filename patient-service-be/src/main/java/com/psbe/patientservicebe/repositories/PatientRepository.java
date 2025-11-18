package com.psbe.patientservicebe.repositories;

import com.psbe.patientservicebe.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {//I used generic JpaRepository to provide CRUD operations for Patient entities/models.

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID id);//this will check if a patient with the given email exists excluding the patient with the specified id.//SQL: SELECT COUNT(*) > 0 FROM patients WHERE email = ? AND id <> ?;

}
