package Proiect.IP.service;

import Proiect.IP.model.Patient;
import Proiect.IP.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }
    public Patient save(Patient patient) {
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        return patientRepository.save(patient);
    }
    public void  deleteAll() {
       patientRepository.deleteAll();
    }


}
