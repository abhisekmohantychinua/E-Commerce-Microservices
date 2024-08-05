package dev.abhisek.userservice.exceptions.models;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static org.springframework.http.HttpStatus.*;


@Getter
public enum BusinessError {
    INVALID_ARGUMENT("100", BAD_REQUEST, "This error occurs when invalid attributes/arguments provided."),
    USER_NOT_FOUND("114", NOT_FOUND, "This error occurs when requested user is not found on server with provided credentials."),
    ADDRESS_NOT_FOUND("124", NOT_FOUND, "This error occurs when requested address is not found on server with provided credentials."),
    IMAGE_UNSUPPORTED("105", UNSUPPORTED_MEDIA_TYPE, "This error occurs when the provided image has an unsupported extension.", "This error occurs when the filename is invalid/illegal."),
    SERVER_ERROR("106", INTERNAL_SERVER_ERROR, "This error occurs when there is something unexpected occur in the server.", "This error occurs when there is some IO error occurs during file handling.");
    private final String code;
    private final Set<String> description;
    private final HttpStatus status;

    BusinessError(String code, HttpStatus status, String... description) {
        this.code = code;
        this.status = status;
        this.description = Set.of(description);
    }
}
