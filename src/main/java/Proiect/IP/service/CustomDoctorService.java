package Proiect.IP.service;

import javax.print.Doc;

import Proiect.IP.DTO.PatientUpdateDTO;
import Proiect.IP.details.DoctorDetails;
import Proiect.IP.model.Doctor;
import Proiect.IP.model.Patient;
import Proiect.IP.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service("customDoctorService")
@AllArgsConstructor
public class CustomDoctorService implements UserDetailsService {
    private final DoctorRepository doctorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor = doctorRepository.findByEmail(username);
        if (doctor == null) {
            throw new UsernameNotFoundException("Doctor not found with email: " + username);
        }
        return new DoctorDetails(
                doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getPhone(),
                doctor.getPassword()
        );
    }


}
