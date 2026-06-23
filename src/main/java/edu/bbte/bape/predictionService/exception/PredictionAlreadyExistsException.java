package edu.bbte.bape.predictionService.exception;

public class PredictionAlreadyExistsException extends RuntimeException {

    public PredictionAlreadyExistsException() {
        super("Prediction already exists for this user and match");
    }
}