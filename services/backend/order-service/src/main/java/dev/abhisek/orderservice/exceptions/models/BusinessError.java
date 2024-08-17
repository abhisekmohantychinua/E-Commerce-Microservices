package dev.abhisek.orderservice.exceptions.models;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BusinessError {
    INVALID_ARGUMENT("300", BAD_REQUEST, "This error occurs when invalid attributes/arguments provided."),
    UNSUPPORTED_OPERATION("305", METHOD_NOT_ALLOWED, "This error occurs when you try to do something i.e. unsupported by the server."),
    OUT_OF_STOCK("307", CONFLICT, "This error occurs only when requested product is out of stock."),
    CART_NOT_FOUND("324", NOT_FOUND, "This error occurs when requested cart is not found on server."),
    ORDER_NOT_FOUND("314", NOT_FOUND, "This error occurs when requested order is not found on server."),
    PRODUCT_NOT_FOUND("204", NOT_FOUND, "This error occurs when requested product is not found on server."),
    USER_NOT_FOUND("114", NOT_FOUND, "This error occurs when requested user is not found on server with provided credentials."),
    ADDRESS_NOT_FOUND("124", NOT_FOUND, "This error occurs when requested address is not found on server with provided credentials."),
    SERVER_ERROR("306", INTERNAL_SERVER_ERROR, "This error occurs when there is something unexpected occur in the server.");
    private final String code;
    private final HttpStatus status;
    private final Set<String> description;

    BusinessError(String code, HttpStatus status, String... description) {
        this.code = code;
        this.status = status;
        this.description = Set.of(description);
    }
}
