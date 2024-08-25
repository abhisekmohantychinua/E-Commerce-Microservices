package dev.abhisek.reviewservice.exceptions.models;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BusinessError {
    USER_SERVICE_DOWN("1**", SERVICE_UNAVAILABLE, "This error occurs when user-service is down or under maintenance."),
    PRODUCT_SERVICE_DOWN("2**", SERVICE_UNAVAILABLE, "This error occurs when product-service is down or under maintenance."),
    INVALID_ARGUMENT("500", BAD_REQUEST, "This error occurs when invalid attributes/arguments provided."),
    USER_NOT_FOUND("114", NOT_FOUND, "This error occurs when requested user is not found on server with provided credentials."),
    PRODUCT_NOT_FOUND("204", NOT_FOUND, "This error occurs when requested product is not found on server."),
    REVIEW_NOT_FOUND("504", NOT_FOUND, "This error occurs when requested review is not found on server."),
    PERMISSION_DENIED("502", FORBIDDEN, "This error occurs when you are trying to access/modify other users entity."),
    SERVER_ERROR("506", INTERNAL_SERVER_ERROR, "This error occurs when there is something unexpected occur in the server.", "This error occurs when there is some IO error occurs during file handling.");
    private final String code;
    private final HttpStatus status;
    private final Set<String> description;

    BusinessError(String code, HttpStatus status, String... description) {
        this.code = code;
        this.status = status;
        this.description = Set.of(description);
    }
}
