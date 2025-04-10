package Proiect.IP.repository;

import Proiect.IP.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
    //Optional<Patient> findByEmail(String email);
    Patient findByEmail(String email);
    // Optional<Patient> findById(String id);
    boolean existsByEmail(String userEmail);


}
