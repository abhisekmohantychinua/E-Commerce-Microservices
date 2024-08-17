package dev.abhisek.paymentservice.exceptions.models;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String s) {
        super(s);
    }
}
