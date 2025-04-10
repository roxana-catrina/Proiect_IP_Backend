package Proiect.IP.service;

import Proiect.IP.DTO.PatientUpdateDTO;
import Proiect.IP.details.DoctorDetails;
import Proiect.IP.details.PatientDetails;
import Proiect.IP.model.Doctor;
import Proiect.IP.model.Patient;
import Proiect.IP.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@AllArgsConstructor
@Service("customPatientService")
public class CustomPatientService implements UserDetailsService {
    private PatientRepository patientRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByEmail(username);
        return new PatientDetails(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getCNP(),
                patient.getAge(),
                patient.getAddress(),
                patient.getPhone(),
                patient.getEmail(),
                patient.getMedicalHistory(),
                patient.getDoctorId(),
                patient.getPassword()
        );
    }


}
