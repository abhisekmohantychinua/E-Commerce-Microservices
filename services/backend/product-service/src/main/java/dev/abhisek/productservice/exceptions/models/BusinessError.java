package dev.abhisek.productservice.exceptions.models;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BusinessError {
    INVALID_ARGUMENT("200", BAD_REQUEST, "This error occurs when invalid attributes/arguments provided."),
    PRODUCT_NOT_FOUND("204", NOT_FOUND, "This error occurs when requested product is not found on server."),
    IMAGE_UNSUPPORTED("205", UNSUPPORTED_MEDIA_TYPE, "This error occurs when the provided image has an unsupported extension.", "This error occurs when the filename is invalid/illegal."),
    SERVER_ERROR("206", INTERNAL_SERVER_ERROR, "This error occurs when there is something unexpected occur in the server.", "This error occurs when there is some IO error occurs during file handling.");
    private final String code;
    private final HttpStatus status;
    private final Set<String> description;

    BusinessError(String code, HttpStatus status, String... description) {
        this.code = code;
        this.status = status;
        this.description = Set.of(description);
    }
}
