package Proiect.IP.controller;

import Proiect.IP.Authentication.AuthenticationRequest;
import Proiect.IP.Authentication.AuthenticationResponse;
import Proiect.IP.DTO.PatientUpdateDTO;
import Proiect.IP.configuration.JwtUtil;
import Proiect.IP.model.*;
import Proiect.IP.repository.DoctorRepository;
import Proiect.IP.repository.PatientRepository;
import Proiect.IP.service.CustomPatientService;
import Proiect.IP.service.DoctorService;
import Proiect.IP.service.PatientService;
import com.mongodb.client.result.DeleteResult;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor

public class PatientController {
    private final PatientService patientService;
    private final PatientRepository patientRepository;
    private final DoctorService doctorService;
    private final JwtUtil jwtUtil;
    private final CustomPatientService customPatientService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/patients")
    public List<Patient> getAllPatiens() {
        return patientService.getAll();
    }

    //atunci cand se logheaza un nou pacient trebuie sa aleaga numele doctorului la care se afla .
    //cautam  dupa nume medicul ca sa aflam idul
    // checkbox cu nuemele doctorilor
    @PostMapping("/patients")
    public ResponseEntity<?> createPatient(@RequestBody Patient patient, @RequestParam String nameDoctor) {
        try {

            System.out.println("Nume doctor: " + nameDoctor);
            System.out.println(nameDoctor);
            Optional<Doctor> doctor = doctorService.findByName(nameDoctor);
            System.out.println("doctor: " + doctor);

            if (patient == null || nameDoctor == null) {
                return ResponseEntity.badRequest().body(null);
            } else {

                if (doctor.isEmpty()) {
                    return ResponseEntity.badRequest().body("Nu s-a găsit doctorul cu numele: " + nameDoctor);

                }
                patient.setDoctorId(doctor.get().getId().toString());
                Patient savedDPatient = patientService.save(patient);
                System.out.println(doctor.get().getId().toString());
                return ResponseEntity.status(HttpStatus.CREATED).body(savedDPatient);
            }
        } catch (Exception e) {
            System.out.println("=== Date primite din frontend ===");

            System.out.println("datele:    " + patient.getEmail() + patient.getPhone());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Eroare la salvarea pacientului. Detalii: " + e.getMessage(), e);
        } finally {
            System.out.println("Pacient: " + patient);
            System.out.println("Nume doctor: " + nameDoctor);
        }
    }
    @Autowired
    private MongoTemplate mongoTemplate;

    @DeleteMapping("/patients/{email}")
    public void deletePatients(@PathVariable String email) {
        Query query = new Query(Criteria.where("email").is(email));
        Patient patient=patientRepository.findByEmail(email);
           String objectId = patient.getId();
        System.out.println("Pacient: idd " + objectId);
        DeleteResult result = mongoTemplate.remove(query, Patient.class);
        Query prescriptionQuery = new Query(Criteria.where("patientId").is(objectId));
        mongoTemplate.remove(prescriptionQuery, Alert.class);


        Query recommendationQuery = new Query(Criteria.where("patientId").is(objectId));
        mongoTemplate.remove(recommendationQuery , Recommendation.class);

        Query patientQuery = new Query(Criteria.where("patientId").is(objectId));
        mongoTemplate.remove(patientQuery, Sensor.class);
        System.out.println("Deleted count: " + result.getDeletedCount());
        if (result.getDeletedCount() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found");
        }





    }
    @PostMapping("/patient/login")
    public ResponseEntity<?> patientLogin(@RequestBody AuthenticationRequest authenticationRequest) {
        // 1. Verifică dacă utilizatorul există înainte de autentificare

        Optional<Patient> optionalPatient = Optional.ofNullable(patientRepository.findByEmail(authenticationRequest.getEmail()));
        if (optionalPatient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Utilizatorul nu există"));
        }

        // 2. Dacă utilizatorul există, încercăm autentificarea
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Parola este incorectă"));
        }

        // 3. Generăm token-ul și returnăm utilizatorul
        Patient patient = optionalPatient.get();
        final String token = jwtUtil.generateToken(customPatientService.loadUserByUsername(patient.getEmail()));

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwt(token);
        authenticationResponse.setEmail(patient.getEmail());
        // authenticationResponse.setNume(user.getNume());

        return ResponseEntity.ok(authenticationResponse);
    }

    @GetMapping("/patients/email/{email}")
    public ResponseEntity<?> getPatientByEmail(@PathVariable String email) {
        Optional<Patient> optionalPatient = Optional.ofNullable(patientRepository.findByEmail(email));
        return ResponseEntity.ok(optionalPatient);
    }

    @PutMapping("/patient/{id}")
    public ResponseEntity<?> updatePatient(
            @PathVariable String id,
            @RequestBody PatientUpdateDTO updateDTO) {

        Patient updatedPatient = patientService.updatePatient(id, updateDTO);

        return ResponseEntity.ok(updatedPatient);
    }
}