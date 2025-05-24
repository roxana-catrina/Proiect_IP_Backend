package Proiect.IP.controller;

import Proiect.IP.model.Recommendation;
import Proiect.IP.model.Sensor;

import Proiect.IP.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:8083")
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor
public class SensorController {
    private final SensorService sensorService;
    @GetMapping("/sensors")
    public List<Sensor> getAll() {
        return sensorService.findAll();
    }


    @PostMapping("/sensors")
    public ResponseEntity<Sensor> create(@RequestBody Sensor sensor) {
        try {
            if (sensor == null   ) {
                return ResponseEntity.badRequest().body(null);
            }

            Sensor savedDSensor =sensorService.save(sensor);
           
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDSensor);

        } catch (Exception e) {
            
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Eroare la salvarea senzorului . Detalii: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/sensors")
    public void deleteAll() {
        sensorService.deleteAll();
    }


    @GetMapping("/sensors/{idPatient}")
    public ResponseEntity<?> getPatientSensors(@PathVariable String idPatient) {
        List<Sensor> sensors = sensorService.findAllByPatientId(idPatient);
        if(sensors.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nu exista recomandari");

        }
        return ResponseEntity.status(HttpStatus.CREATED).body(sensors);
    }


    @GetMapping("/sensors/latest/{patientId}")
    public ResponseEntity<Sensor> getLatestSensorData(@PathVariable String patientId) {
        Sensor sensor = sensorService.getLatestSensorData(patientId);
        return ResponseEntity.ok(sensor);
    }

    @GetMapping("/sensors/patient/{patientId}")
    public ResponseEntity<List<Sensor>> getPatientSensorData(@PathVariable String patientId) {
        List<Sensor> sensors = sensorService.getPatientSensorData(patientId);
        return ResponseEntity.ok(sensors);
    }

    @PostMapping
    public ResponseEntity<Sensor> saveSensorData(@RequestBody Sensor sensor) {
        Sensor savedSensor = sensorService.saveSensorData(sensor);
        return ResponseEntity.ok(savedSensor);
    }

    @GetMapping("/sensors/{idPatient}/latest")
    public ResponseEntity<?> getLatestPatientSensor(@PathVariable String idPatient) {
        Sensor latestSensor = sensorService.findLatestByPatientId(idPatient);
        if(latestSensor == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sensor data available");
        }
        return ResponseEntity.ok(latestSensor);
    }

   /* @PostMapping("/sensors/generate-test/{patientId}")
    public ResponseEntity<String> generateTestData(@PathVariable String patientId) {
        sensorService.generateTestData(patientId);
        return ResponseEntity.ok("Test data generated successfully");
    }*/
}
