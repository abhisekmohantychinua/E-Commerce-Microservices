package dev.abhisek.orderservice.exceptions.models;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String s) {
        super(s);
    }
}
