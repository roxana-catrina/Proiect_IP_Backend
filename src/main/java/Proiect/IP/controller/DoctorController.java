package Proiect.IP.controller;

import lombok.AllArgsConstructor;
import Proiect.IP.model.Doctor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Proiect.IP.service.DoctorService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@AllArgsConstructor

public class DoctorController {

    private final DoctorService doctorService;
    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
      /*  for(Doctor d : doctorService.findAll())
            System.out.println(d.getId()+"   ");*/
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

}
