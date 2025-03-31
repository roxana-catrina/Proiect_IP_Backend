package Proiect.IP.repository;

import Proiect.IP.model.Doctor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, ObjectId> {

  Optional<Doctor> findByName(String name);

  Doctor findByEmail(String email);

  boolean existsByEmail(String userEmail);
}
