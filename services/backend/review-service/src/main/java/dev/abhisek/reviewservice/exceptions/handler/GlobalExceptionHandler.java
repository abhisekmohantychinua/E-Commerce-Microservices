package dev.abhisek.reviewservice.exceptions.handler;

import dev.abhisek.reviewservice.exceptions.dto.ExceptionResponse;
import dev.abhisek.reviewservice.exceptions.models.PermissionException;
import dev.abhisek.reviewservice.exceptions.models.ProductNotFoundException;
import dev.abhisek.reviewservice.exceptions.models.ReviewNotFoundException;
import dev.abhisek.reviewservice.exceptions.models.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static dev.abhisek.reviewservice.exceptions.models.BusinessError.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
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

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(ReviewNotFoundException e) {
        return ResponseEntity
                .status(REVIEW_NOT_FOUND.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(REVIEW_NOT_FOUND.getCode())
                                .status(REVIEW_NOT_FOUND.getStatus().toString())
                                .messages(List.of(e.getLocalizedMessage()))
                                .details(REVIEW_NOT_FOUND.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<ExceptionResponse> handleException(PermissionException e) {
        return ResponseEntity
                .status(PERMISSION_DENIED.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(PERMISSION_DENIED.getCode())
                                .status(PERMISSION_DENIED.getStatus().toString())
                                .details(PERMISSION_DENIED.getDescription())
                                .messages(List.of(e.getLocalizedMessage()))
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
