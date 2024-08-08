package dev.abhisek.reviewservice.exceptions.models;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(String s) {
        super(s);
    }
}
