package dev.abhisek.paymentservice.exceptions.models;

public class ServiceDownException extends RuntimeException {
    public ServiceDownException(String message) {
        super(message);
    }
}
