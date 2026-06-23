package edu.bbte.bape.predictionService.exception;

public class PredictionNotFoundException extends RuntimeException {

    public PredictionNotFoundException(Long id) {
        super("Prediction not found with id: " + id);
    }
}