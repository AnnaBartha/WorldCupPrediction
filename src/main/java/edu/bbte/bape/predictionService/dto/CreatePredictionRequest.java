package edu.bbte.bape.predictionService.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePredictionRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Match id is required")
    private Long matchId;

    @NotNull(message = "Predicted home score is required")
    @Min(value = 0, message = "Predicted home score cannot be negative")
    private Integer predictedHomeScore;

    @NotNull(message = "Predicted away score is required")
    @Min(value = 0, message = "Predicted away score cannot be negative")
    private Integer predictedAwayScore;
}