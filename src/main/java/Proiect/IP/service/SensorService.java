package Proiect.IP.service;

import Proiect.IP.model.Sensor;
import Proiect.IP.repository.SensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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


    public Sensor getLatestSensorData(String patientId) {
        return sensorRepository.findFirstByPatientIdOrderByTimestampDesc(patientId);
    }

    public List<Sensor> getPatientSensorData(String patientId) {
        return sensorRepository.findByPatientId(patientId);
    }

    public Sensor saveSensorData(Sensor sensor) {
        sensor.setTimestamp(LocalDateTime.now());
        return sensorRepository.save(sensor);
    }
    public Sensor findLatestByPatientId(String patientId) {
        List<Sensor> sensors = sensorRepository.findByPatientIdOrderByTimestampDesc(patientId);
        return sensors.isEmpty() ? null : sensors.get(0);
    }

    /*public void generateTestData(String patientId) {
        List<Sensor> testData = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < 50; i++) {
            Sensor sensor = new Sensor();
            sensor.setPatientId(patientId);
            // Simulate EKG signal (-1.5 to 1.5)
            sensor.setEkgSignal(String.valueOf(Math.sin(i * 0.2) * 1.5));
            // Simulate heart rate (60-100 BPM)
            sensor.setHeartRate(String.valueOf(70 + (int)(Math.random() * 30)));
            // Simulate temperature (36-37.5°C)
           sensor.setTemperature(36.0 + Math.random() * 1.5);
            // Simulate humidity (40-60%)
           sensor.setHumidity(40.0 + Math.random() * 20);
            // Set timestamp with 1-minute intervals
            sensor.setTimestamp(now.minusMinutes(50 - i));

            testData.add(sensor);
        }

        sensorRepository.saveAll(testData);
    }*/
}
