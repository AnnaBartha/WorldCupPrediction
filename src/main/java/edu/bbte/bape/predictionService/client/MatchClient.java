package edu.bbte.bape.predictionService.client;

import edu.bbte.bape.predictionService.dto.MatchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class MatchClient {

    private final RestClient restClient;

    public MatchClient(
            @Value("${services.match-service-url}") String matchServiceUrl
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(matchServiceUrl)
                .build();
    }

    public void validateMatchExists(Long matchId) {
        restClient.get()
                .uri("/api/matches/{id}", matchId)
                .retrieve()
                .toBodilessEntity();
    }

    public MatchResponse getMatchById(Long matchId) {
        return restClient.get()
                .uri("/api/matches/{id}", matchId)
                .retrieve()
                .body(MatchResponse.class);
    }
}