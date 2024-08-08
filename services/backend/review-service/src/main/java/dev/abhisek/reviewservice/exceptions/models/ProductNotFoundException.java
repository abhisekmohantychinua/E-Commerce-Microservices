package dev.abhisek.reviewservice.exceptions.models;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String s) {
        super(s);
    }
}
