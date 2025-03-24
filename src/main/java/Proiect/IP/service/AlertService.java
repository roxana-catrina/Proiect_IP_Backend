package Proiect.IP.service;

import Proiect.IP.model.Alert;
import Proiect.IP.repository.AlertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AlertService {
    private final AlertRepository alertRepository;
    public Alert save(Alert alert) {
        return alertRepository.save(alert);
    }
    public List<Alert> findAll() {
        return alertRepository.findAll();
    }

    public void deleteAll(){
        alertRepository.deleteAll();
    }
    public List<Alert> findAllByPatientId(String userId) {
        return alertRepository.findAllByPatientId(userId);
    }
}

