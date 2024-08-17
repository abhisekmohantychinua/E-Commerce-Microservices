package dev.abhisek.paymentservice.exceptions.models;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BusinessError {
    PAYMENT_NOT_FOUND("404", NOT_FOUND, "This error occurs when the requested payment is not found on server."),
    ORDER_NOT_FOUND("314", NOT_FOUND, "This error occurs when the requested payment is not found on server."),
    UNSUPPORTED_OPERATION("405", METHOD_NOT_ALLOWED, "This error occurs when you try to do something i.e. unsupported by the server."),
    INVALID_CREDENTIALS("401", BAD_REQUEST, "This error occurs when invalid credentials are provided.", "This error occurs when invalid attributes/arguments provided."),
    SERVER_ERROR("406", INTERNAL_SERVER_ERROR, "This error occurs when there is something unexpected occur in the server.", "This error occurs when there is some IO error occurs during file handling.");
    private final String code;
    private final HttpStatus status;
    private final Set<String> description;

    BusinessError(String code, HttpStatus status, String... description) {
        this.code = code;
        this.status = status;
        this.description = Set.of(description);
    }
}
