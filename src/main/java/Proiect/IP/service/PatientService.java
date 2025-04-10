package Proiect.IP.service;

import Proiect.IP.DTO.PatientUpdateDTO;
import Proiect.IP.model.Patient;
import Proiect.IP.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void deleteAll() {
        patientRepository.deleteAll();
    }

    public Patient updatePatient(String patientId, PatientUpdateDTO updateDTO) {
        if (updateDTO!=null) {
            //System.out.println( "idddd"+existingPatient1.get().getId());
            System.out.println(patientId);

           System.out.println( patientRepository.findByEmail(patientId.trim()));

                Patient existingPatient = patientRepository.findByEmail(patientId.trim());
            if (existingPatient!=null) {
                 existingPatient.setId(updateDTO.getId());
                // Update only non-null fields
                System.out.println(existingPatient);
                if (updateDTO.getFirstName() != null) {
                    existingPatient.setFirstName(updateDTO.getFirstName());
                }
                if (updateDTO.getLastName() != null) {
                    existingPatient.setLastName(updateDTO.getLastName());
                }

                if (updateDTO.getPhone() != null) {
                    existingPatient.setPhone(updateDTO.getPhone());
                }
                if (updateDTO.getAddress() != null) {
                    existingPatient.setAddress(updateDTO.getAddress());
                }
                if (updateDTO.getMedicalHistory() != null) {
                    existingPatient.setMedicalHistory(updateDTO.getMedicalHistory());
                }
                if (updateDTO.getAge() != 0) {
                    existingPatient.setAge(updateDTO.getAge());
                }
                patientRepository.deleteById(updateDTO.getId());
                System.out.println("eu intraiaici acum");
                System.out.println(existingPatient);
                return patientRepository.save(existingPatient);
            }
        }

        return null;
    }
}