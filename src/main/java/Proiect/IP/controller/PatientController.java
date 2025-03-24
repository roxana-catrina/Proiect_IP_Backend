package Proiect.IP.controller;

import Proiect.IP.model.Doctor;
import Proiect.IP.model.Patient;
import Proiect.IP.service.DoctorService;
import Proiect.IP.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor

public class PatientController {
    private final PatientService patientService;

    private final DoctorService doctorService;
    @GetMapping("/patients")
    public List<Patient> getAllPatiens() {
        return patientService.getAll();
    }

    //atunci cand se logheaza un nou pacient trebuie sa aleaga numele doctorului la care se afla .
    //cautam  dupa nume medicul ca sa aflam idul
    // checkbox cu nuemele doctorilor
    @PostMapping("/patient")
    public ResponseEntity<?> createPatient(@RequestBody Patient patient , @RequestParam String nameDoctor ) {
        try { System.out.println(nameDoctor);
            Optional< Doctor> doctor = doctorService.findByName(nameDoctor);
            System.out.println("doctor: " + doctor);

            if (patient == null  || nameDoctor == null  ) {
                return ResponseEntity.badRequest().body(null);
            }

else {

    if(doctor.isEmpty()) {
        return ResponseEntity.badRequest().body("Nu s-a găsit doctorul cu numele: " + nameDoctor);

    }          patient.setDoctorId(doctor.get().getId().toString());
                Patient savedDPatient = patientService.save(patient);
                System.out.println(doctor.get().getId().toString());
                return ResponseEntity.status(HttpStatus.CREATED).body(savedDPatient);
            }
        } catch (Exception e) {
            System.out.println("datele:    " + patient.getEmail()+patient.getPhone());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Eroare la salvarea pacientului. Detalii: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/patients")
    public void deletePatients() {
        patientService.deleteAll();
    }
}
