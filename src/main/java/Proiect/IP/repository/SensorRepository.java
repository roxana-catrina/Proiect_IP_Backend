package Proiect.IP.repository;

import Proiect.IP.model.Sensor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends MongoRepository<Sensor, String> {
    List<Sensor> findAllByPatientId(String patientId);
}
