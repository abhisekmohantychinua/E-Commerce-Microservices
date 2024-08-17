package dev.abhisek.orderservice.exceptions.models;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String s) {
        super(s);
    }
}
