package dev.abhisek.reviewservice.exceptions.models;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String s) {
        super(s);
    }
}
