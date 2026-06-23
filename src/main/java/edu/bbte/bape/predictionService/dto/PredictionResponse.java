package edu.bbte.bape.predictionService.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PredictionResponse {

    private Long id;
    private Long userId;
    private Long matchId;
    private Integer predictedHomeScore;
    private Integer predictedAwayScore;
    private Integer points;
    private LocalDateTime createdAt;
}