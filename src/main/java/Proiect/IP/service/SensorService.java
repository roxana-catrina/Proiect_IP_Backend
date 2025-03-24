package Proiect.IP.service;

import Proiect.IP.model.Sensor;
import Proiect.IP.repository.SensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SensorService {
    private final SensorRepository sensorRepository;
    public Sensor save(Sensor sensor) {
        return sensorRepository.save(sensor);
    }
    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }
    public void deleteAll(){
        sensorRepository.deleteAll();
    }
  public List<Sensor> findAllByPatientId(String patientId) {
        return sensorRepository.findAllByPatientId(patientId);
   }
}
