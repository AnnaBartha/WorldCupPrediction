package edu.bbte.bape.predictionService.service;

import edu.bbte.bape.predictionService.client.MatchClient;
import edu.bbte.bape.predictionService.client.UserClient;

import edu.bbte.bape.predictionService.dto.LeaderboardResponse;
import edu.bbte.bape.predictionService.dto.MatchResponse;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import edu.bbte.bape.predictionService.dto.CreatePredictionRequest;
import edu.bbte.bape.predictionService.dto.PredictionResponse;
import edu.bbte.bape.predictionService.entity.Prediction;
import edu.bbte.bape.predictionService.exception.PredictionAlreadyExistsException;
import edu.bbte.bape.predictionService.exception.PredictionNotFoundException;
import edu.bbte.bape.predictionService.repository.PredictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PredictionService {

    private final PredictionRepository predictionRepository;
    private final UserClient userClient;

    private final MatchClient matchClient;

    public PredictionResponse createPrediction(CreatePredictionRequest request) {

        userClient.validateUserExists(request.getUserId());

        matchClient.validateMatchExists(request.getMatchId());

        if (predictionRepository.existsByUserIdAndMatchId(request.getUserId(), request.getMatchId())) {
            throw new PredictionAlreadyExistsException();
        }

        Prediction prediction = Prediction.builder()
                .userId(request.getUserId())
                .matchId(request.getMatchId())
                .predictedHomeScore(request.getPredictedHomeScore())
                .predictedAwayScore(request.getPredictedAwayScore())
                .points(0)
                .build();

        Prediction savedPrediction = predictionRepository.save(prediction);

        return mapToResponse(savedPrediction);
    }

    public List<PredictionResponse> getPredictionsByUserId(Long userId) {
        return predictionRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<PredictionResponse> getPredictionsByMatchId(Long matchId) {
        return predictionRepository.findByMatchId(matchId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public PredictionResponse getPredictionById(Long id) {
        Prediction prediction = predictionRepository.findById(id)
                .orElseThrow(() -> new PredictionNotFoundException(id));

        return mapToResponse(prediction);
    }

    private PredictionResponse mapToResponse(Prediction prediction) {
        return PredictionResponse.builder()
                .id(prediction.getId())
                .userId(prediction.getUserId())
                .matchId(prediction.getMatchId())
                .predictedHomeScore(prediction.getPredictedHomeScore())
                .predictedAwayScore(prediction.getPredictedAwayScore())
                .points(prediction.getPoints())
                .createdAt(prediction.getCreatedAt())
                .build();
    }

    public List<PredictionResponse> calculatePointsForMatch(Long matchId) {
        MatchResponse match = matchClient.getMatchById(matchId);

        if (!"FINISHED".equals(match.getStatus())) {
            throw new RuntimeException("Match is not finished yet");
        }

        List<Prediction> predictions = predictionRepository.findByMatchId(matchId);

        predictions.forEach(prediction -> {
            int points = calculatePoints(
                    prediction.getPredictedHomeScore(),
                    prediction.getPredictedAwayScore(),
                    match.getHomeScore(),
                    match.getAwayScore()
            );

            prediction.setPoints(points);
        });

        return predictionRepository.saveAll(predictions)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<LeaderboardResponse> getLeaderboard() {
        Map<Long, Integer> pointsByUser = predictionRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Prediction::getUserId,
                        Collectors.summingInt(Prediction::getPoints)
                ));

        return pointsByUser.entrySet()
                .stream()
                .map(entry -> LeaderboardResponse.builder()
                        .userId(entry.getKey())
                        .totalPoints(entry.getValue())
                        .build())
                .sorted(Comparator.comparing(LeaderboardResponse::getTotalPoints).reversed())
                .toList();
    }

    private int calculatePoints(
            Integer predictedHomeScore,
            Integer predictedAwayScore,
            Integer realHomeScore,
            Integer realAwayScore
    ) {
        if (predictedHomeScore.equals(realHomeScore)
                && predictedAwayScore.equals(realAwayScore)) {
            return 3;
        }

        int predictedResult = Integer.compare(predictedHomeScore, predictedAwayScore);
        int realResult = Integer.compare(realHomeScore, realAwayScore);

        if (predictedResult == realResult) {
            return 1;
        }

        return 0;
    }
}