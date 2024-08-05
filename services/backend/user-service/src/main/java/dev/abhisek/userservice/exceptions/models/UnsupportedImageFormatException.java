package dev.abhisek.userservice.exceptions.models;

public class UnsupportedImageFormatException extends RuntimeException {
    public UnsupportedImageFormatException(String message) {
        super(message);
    }
}
