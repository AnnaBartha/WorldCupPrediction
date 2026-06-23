package edu.bbte.bape.predictionService.controller;

import edu.bbte.bape.predictionService.dto.LeaderboardResponse;

import edu.bbte.bape.predictionService.dto.CreatePredictionRequest;
import edu.bbte.bape.predictionService.dto.PredictionResponse;
import edu.bbte.bape.predictionService.service.PredictionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/predictions")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @PostMapping
    public PredictionResponse createPrediction(
            @Valid @RequestBody CreatePredictionRequest request
    ) {
        return predictionService.createPrediction(request);
    }

    @GetMapping("/{id}")
    public PredictionResponse getPredictionById(
            @PathVariable Long id
    ) {
        return predictionService.getPredictionById(id);
    }

    @GetMapping("/user/{userId}")
    public List<PredictionResponse> getPredictionsByUserId(
            @PathVariable Long userId
    ) {
        return predictionService.getPredictionsByUserId(userId);
    }

    @GetMapping("/match/{matchId}")
    public List<PredictionResponse> getPredictionsByMatchId(
            @PathVariable Long matchId
    ) {
        return predictionService.getPredictionsByMatchId(matchId);
    }

    @PutMapping("/calculate/match/{matchId}")
    public List<PredictionResponse> calculatePointsForMatch(
            @PathVariable Long matchId
    ) {
        return predictionService.calculatePointsForMatch(matchId);
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardResponse> getLeaderboard() {
        return predictionService.getLeaderboard();
    }
}