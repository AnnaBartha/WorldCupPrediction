package edu.bbte.bape.predictionService.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UserClient {

    private final RestClient restClient;

    public UserClient(
            @Value("${services.user-service-url}") String userServiceUrl
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(userServiceUrl)
                .build();
    }

    public void validateUserExists(Long userId) {
        restClient.get()
                .uri("/api/users/{id}", userId)
                .retrieve()
                .toBodilessEntity();
    }
}