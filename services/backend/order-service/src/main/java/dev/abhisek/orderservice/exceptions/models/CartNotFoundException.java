package dev.abhisek.orderservice.exceptions.models;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String s) {
        super(s);

    }
}
