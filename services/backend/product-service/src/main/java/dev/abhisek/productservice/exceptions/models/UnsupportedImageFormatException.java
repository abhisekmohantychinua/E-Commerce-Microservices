package dev.abhisek.productservice.exceptions.models;

public class UnsupportedImageFormatException extends RuntimeException {
    public UnsupportedImageFormatException(String s) {
        super(s);
    }
}
