package dev.abhisek.paymentservice.exceptions.handler;

import dev.abhisek.paymentservice.exceptions.dto.ExceptionResponse;
import dev.abhisek.paymentservice.exceptions.models.InvalidCredentialException;
import dev.abhisek.paymentservice.exceptions.models.OrderNotFoundException;
import dev.abhisek.paymentservice.exceptions.models.PaymentNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static dev.abhisek.paymentservice.exceptions.models.BusinessError.*;

@RestControllerAdvice(annotations = RestController.class)
public class GlobalRestExceptionHandler {
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(PaymentNotFoundException e) {
        return ResponseEntity
                .status(PAYMENT_NOT_FOUND.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(PAYMENT_NOT_FOUND.getCode())
                                .status(PAYMENT_NOT_FOUND.getStatus().toString())
                                .messages(List.of(e.getLocalizedMessage()))
                                .details(PAYMENT_NOT_FOUND.getDescription())
                                .build()
                );
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public ResponseEntity<ExceptionResponse> handleException(InvalidCredentialException e) {
        return ResponseEntity
                .status(INVALID_CREDENTIALS.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(INVALID_CREDENTIALS.getCode())
                                .status(INVALID_CREDENTIALS.getStatus().toString())
                                .messages(List.of(e.getLocalizedMessage()))
                                .details(INVALID_CREDENTIALS.getDescription())
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException e) {
        List<String> messages = new ArrayList<>(
                e
                        .getBindingResult()
                        .getAllErrors().stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList());
        return ResponseEntity
                .status(INVALID_CREDENTIALS.getStatus())
                .body(
                        ExceptionResponse.builder()
                                .code(INVALID_CREDENTIALS.getCode())
                                .status(INVALID_CREDENTIALS.getStatus().toString())
                                .messages(messages)
                                .details(INVALID_CREDENTIALS.getDescription())
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
