package Proiect.IP.controller;

import Proiect.IP.model.Alert;

import Proiect.IP.service.AlertService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor

public class AlertController {

    private final AlertService alertService;
    @GetMapping("/alerts")
    public List<Alert> getAlerts() {
      /*  for(Alert d : alertservice.findAll())
            System.out.println(d.getId()+"   ");*/
        return alertService.findAll();
    }


    @PostMapping("/alert")
    public ResponseEntity<Alert> createAlert(@RequestBody Alert alert) {
        try {
            if (alert == null   ) {
                return ResponseEntity.badRequest().body(null);
            }

            Alert savedAlert = alertService.save(alert);
            ///System.out.println("Alert salvat cu ID: " + savedAlert.getId()); // Adaugă această linie
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAlert);

        } catch (Exception e) {
            // System.out.println("datele:"+Alert.getName()+"    "+ Alert.getId() + Alert.getEmail()+Alert.getSpecialization()+Alert.getPhone());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Eroare la salvarea Alertului. Detalii: " + e.getMessage(), e);
        }
    }
    @DeleteMapping("/alerts")
    public void deletePatients() {
        alertService.deleteAll();
    }

    @GetMapping("/{idPatient}/alerts")
    public ResponseEntity<?> getPacientAlerts(@PathVariable String idPatient) {
        List<Alert > alerts = alertService.findAllByPatientId(idPatient);
        if(alerts.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nu exista alarme");

        }
        return ResponseEntity.status(HttpStatus.CREATED).body(alerts);
    }


}
