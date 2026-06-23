package edu.bbte.bape.predictionService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchResponse {

    private Long id;
    private Integer homeScore;
    private Integer awayScore;
    private String status;
}