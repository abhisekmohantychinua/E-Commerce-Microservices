package dev.abhisek.orderservice.exceptions.models;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String s) {
        super(s);
    }
}
