package dev.abhisek.orderservice.exceptions.handler;

import dev.abhisek.orderservice.exceptions.dto.ExceptionResponse;
import dev.abhisek.orderservice.exceptions.models.UnsupportedOperationException;
import dev.abhisek.orderservice.exceptions.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static dev.abhisek.orderservice.exceptions.models.BusinessError.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(ProductNotFoundException e) {
        return ResponseEntity
                .status(PRODUCT_NOT_FOUND.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(PRODUCT_NOT_FOUND.getCode())
                                .status(PRODUCT_NOT_FOUND.getStatus().toString())
                                .messages(List.of(e.getLocalizedMessage()))
                                .details(PRODUCT_NOT_FOUND.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UserNotFoundException e) {
        return ResponseEntity
                .status(USER_NOT_FOUND.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(USER_NOT_FOUND.getCode())
                                .status(USER_NOT_FOUND.getStatus().toString())
                                .messages(List.of(e.getMessage()))
                                .details(USER_NOT_FOUND.getDescription())
                                .build()
                );

    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(AddressNotFoundException e) {
        return ResponseEntity
                .status(ADDRESS_NOT_FOUND.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(ADDRESS_NOT_FOUND.getCode())
                                .status(ADDRESS_NOT_FOUND.getStatus().toString())
                                .messages(List.of(e.getMessage()))
                                .details(ADDRESS_NOT_FOUND.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(OrderNotFoundException e) {
        return ResponseEntity
                .status(ORDER_NOT_FOUND.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(ORDER_NOT_FOUND.getCode())
                                .status(ORDER_NOT_FOUND.getStatus().toString())
                                .messages(List.of(e.getLocalizedMessage()))
                                .details(ORDER_NOT_FOUND.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(CartNotFoundException e) {
        return ResponseEntity
                .status(CART_NOT_FOUND.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(CART_NOT_FOUND.getCode())
                                .status(CART_NOT_FOUND.getStatus().toString())
                                .messages(List.of(e.getLocalizedMessage()))
                                .details(CART_NOT_FOUND.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<ExceptionResponse> handleException(UnsupportedOperationException e) {
        return ResponseEntity
                .status(UNSUPPORTED_OPERATION.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(UNSUPPORTED_OPERATION.getCode())
                                .status(UNSUPPORTED_OPERATION.getStatus().toString())
                                .messages(List.of(e.getLocalizedMessage()))
                                .details(UNSUPPORTED_OPERATION.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ExceptionResponse> handleException(OutOfStockException e) {
        return ResponseEntity
                .status(OUT_OF_STOCK.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(OUT_OF_STOCK.getCode())
                                .status(OUT_OF_STOCK.getStatus().toString())
                                .messages(List.of(e.getLocalizedMessage()))
                                .details(OUT_OF_STOCK.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException e) {
        List<String> messages = new ArrayList<>(
                e
                        .getBindingResult()
                        .getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList());
        return ResponseEntity
                .status(INVALID_ARGUMENT.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(INVALID_ARGUMENT.getCode())
                                .status(INVALID_ARGUMENT.getStatus().toString())
                                .messages(messages)
                                .details(INVALID_ARGUMENT.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(SERVER_ERROR.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(SERVER_ERROR.getCode())
                                .status(SERVER_ERROR.getStatus().toString())
                                .messages(List.of(e.getLocalizedMessage()))
                                .details(SERVER_ERROR.getDescription())
                                .build()
                );
    }
}
