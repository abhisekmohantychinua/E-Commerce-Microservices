package dev.abhisek.reviewservice.exceptions.models;

public class ServiceDownException extends RuntimeException {
    public ServiceDownException(String message) {
        super(message);
    }
}
