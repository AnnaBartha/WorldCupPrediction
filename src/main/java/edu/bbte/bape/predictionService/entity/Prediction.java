package edu.bbte.bape.predictionService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "predictions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Id of the user from user-service
    @Column(nullable = false)
    private Long userId;

    // Id of the match from match-service
    @Column(nullable = false)
    private Long matchId;

    // Predicted goals for home team
    @Column(nullable = false)
    private Integer predictedHomeScore;

    // Predicted goals for away team
    @Column(nullable = false)
    private Integer predictedAwayScore;

    // Calculated points after the real result is available
    @Column(nullable = false)
    private Integer points;

    // Creation timestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void beforeCreate() {
        createdAt = LocalDateTime.now();

        if (points == null) {
            points = 0;
        }
    }
}