package Proiect.IP.controller;

import Proiect.IP.Authentication.AuthenticationRequest;
import Proiect.IP.Authentication.AuthenticationResponse;
import Proiect.IP.configuration.JwtUtil;
import Proiect.IP.details.DoctorDetails;
import Proiect.IP.repository.DoctorRepository;
import Proiect.IP.service.CustomDoctorService;
import lombok.AllArgsConstructor;
import Proiect.IP.model.Doctor;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import Proiect.IP.service.DoctorService;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/api")
@AllArgsConstructor

public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorRepository doctorRepository;
    private final CustomDoctorService customDoctorService;
    private JwtUtil jwtUtil;

    private AuthenticationManager authenticationManager;

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
       for(Doctor d : doctorService.findAll())
            System.out.println(d.getId()+"   ");
        return doctorService.findAll();
    }


    @PostMapping("/doctor")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        try {
            if (doctor == null || doctor.getName() == null  ) {
                return ResponseEntity.badRequest().body(null);
            }

            Doctor savedDoctor = doctorService.save(doctor);
            System.out.println("Doctor salvat cu ID: " + savedDoctor.getId()); // Adaugă această linie
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctor);

        } catch (Exception e) {
           // System.out.println("datele:"+doctor.getName()+"    "+ doctor.getId() + doctor.getEmail()+doctor.getSpecialization()+doctor.getPhone());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Eroare la salvarea doctorului. Detalii: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/doctors")
    public void deleteAll(){
        doctorService.deleteAll();
    }

    @PostMapping("/doctor/login")
    public ResponseEntity<?> doctorLogin(@RequestBody AuthenticationRequest authenticationRequest) {
        // 1. Verifică dacă utilizatorul există înainte de autentificare
        Optional<Doctor> optionalDoctor = Optional.ofNullable(doctorRepository.findByEmail(authenticationRequest.getEmail()));
        if (optionalDoctor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Utilizatorul nu există"));
        }
         System.out.println(optionalDoctor.get().getId());
        System.out.println(authenticationRequest.getPassword());
        System.out.println(authenticationRequest.getEmail());
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
        Doctor doctor = optionalDoctor.get();
        final String token = jwtUtil.generateToken(customDoctorService.loadUserByUsername(doctor.getEmail()));

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwt(token);
        authenticationResponse.setEmail(doctor.getEmail());
       // authenticationResponse.setNume(user.getNume());

        return ResponseEntity.ok(authenticationResponse);
    }

    @GetMapping("doctors/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable ObjectId id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        return optionalDoctor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }
}
