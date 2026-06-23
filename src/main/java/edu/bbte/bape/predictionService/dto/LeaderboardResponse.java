package edu.bbte.bape.predictionService.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LeaderboardResponse {

    private Long userId;
    private Integer totalPoints;
}