package edu.bbte.bape.predictionService.repository;

import edu.bbte.bape.predictionService.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    List<Prediction> findByUserId(Long userId);

    List<Prediction> findByMatchId(Long matchId);

    boolean existsByUserIdAndMatchId(Long userId, Long matchId);
}