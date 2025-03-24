package Proiect.IP.service;

import Proiect.IP.model.Patient;
import Proiect.IP.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }
    public void  deleteAll() {
       patientRepository.deleteAll();
    }


}
