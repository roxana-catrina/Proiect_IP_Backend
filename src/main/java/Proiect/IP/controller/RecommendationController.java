package Proiect.IP.controller;

import Proiect.IP.model.Alert;
import lombok.AllArgsConstructor;
import Proiect.IP.model.Recommendation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Proiect.IP.service.RecommendationService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8083")
@RequestMapping("/api")
@AllArgsConstructor
public class RecommendationController {
    private RecommendationService recommendationService;

  @PostMapping("/recommendations")
    public ResponseEntity<Recommendation> createRecommendation(@RequestBody Recommendation recommendation) {
      if(recommendation==null)
          return ResponseEntity.badRequest().body(null);
      else
        return ResponseEntity.status(HttpStatus.CREATED).body(recommendationService.save(recommendation));
    }

    @GetMapping("/recommendations")
    public List<Recommendation> getAllRecommendations() {
      return recommendationService.findAll();
    }
    @DeleteMapping("/recommendations")
    public void deleteAll(){
      recommendationService.deleteAll();
    }

  @GetMapping("/recommendations/{idPatient}")
  public ResponseEntity<?> getPatientRecommendations(@PathVariable String idPatient) {
    List<Recommendation> recommendations = recommendationService.findAllByPatientId(idPatient);
    if(recommendations.isEmpty()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nu exista recomandari");

    }
    return ResponseEntity.status(HttpStatus.CREATED).body(recommendations);
  }

  @PostMapping("/recommendations/{id}")
  public ResponseEntity<Recommendation> createRecommendation(
          @PathVariable String id,
          @RequestBody Recommendation recommendation) {

    recommendation.setPatientId(id);

    // Presupunem că există un serviciu care salvează recomandarea
    Recommendation savedRecommendation = recommendationService.save(recommendation);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedRecommendation);
  }

}
