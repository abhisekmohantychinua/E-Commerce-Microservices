package dev.abhisek.userservice.exceptions.handler;

import dev.abhisek.userservice.exceptions.dto.ExceptionResponse;
import dev.abhisek.userservice.exceptions.models.AddressNotFoundException;
import dev.abhisek.userservice.exceptions.models.UnsupportedImageFormatException;
import dev.abhisek.userservice.exceptions.models.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static dev.abhisek.userservice.exceptions.models.BusinessError.*;

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

    @ExceptionHandler(UnsupportedImageFormatException.class)
    public ResponseEntity<ExceptionResponse> handleException(UnsupportedImageFormatException e) {
        return ResponseEntity
                .status(IMAGE_UNSUPPORTED.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(IMAGE_UNSUPPORTED.getCode())
                                .status(IMAGE_UNSUPPORTED.getStatus().toString())
                                .messages(List.of(e.getLocalizedMessage()))
                                .details(IMAGE_UNSUPPORTED.getDescription())
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
