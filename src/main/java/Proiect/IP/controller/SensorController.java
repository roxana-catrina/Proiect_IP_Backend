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
//@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api")
@AllArgsConstructor
public class SensorController {
    private final SensorService sensorService;
    @GetMapping("/sensors")
    public List<Sensor> getAll() {
        return sensorService.findAll();
    }


    @PostMapping("/sensor")
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


    @GetMapping("/{idPatient}/sensors")
    public ResponseEntity<?> getPatientSensors(@PathVariable String idPatient) {
        List<Sensor> sensors = sensorService.findAllByPatientId(idPatient);
        if(sensors.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nu exista recomandari");

        }
        return ResponseEntity.status(HttpStatus.CREATED).body(sensors);
    }
}
