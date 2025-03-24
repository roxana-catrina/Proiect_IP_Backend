package Proiect.IP.repository;

import Proiect.IP.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, String> {
    //Recommendation save(Recommendation recommendation);
    List<Recommendation> findAllByPatientId(String id);
}
