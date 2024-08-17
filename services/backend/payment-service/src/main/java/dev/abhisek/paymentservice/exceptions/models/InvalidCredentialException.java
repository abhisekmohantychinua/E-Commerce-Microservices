package dev.abhisek.paymentservice.exceptions.models;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String s) {
        super(s);
    }
}
