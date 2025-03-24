package Proiect.IP.service;

import lombok.AllArgsConstructor;
import Proiect.IP.model.Doctor;
import org.springframework.stereotype.Service;
import Proiect.IP.repository.DoctorRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorService {

    private DoctorRepository doctorRepository;
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }
    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    public Optional<Doctor> findByName(String name) {
        return doctorRepository.findByName(name);
    }
    public void deleteAll(){
doctorRepository.deleteAll();    }

}
