package Proiect.IP.service;

import lombok.AllArgsConstructor;
import Proiect.IP.model.Recommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Proiect.IP.repository.RecommendationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    public Recommendation save(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    public List<Recommendation> findAll() {
        return recommendationRepository.findAll();

    }
    public void deleteAll(){
        recommendationRepository.deleteAll();
    }

    public List<Recommendation>  findAllByPatientId(String patientId) {
        return recommendationRepository.findAllByPatientId(patientId);
    }
}
