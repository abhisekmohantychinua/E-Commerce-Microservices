package dev.abhisek.paymentservice.exceptions.handler;

import dev.abhisek.paymentservice.exceptions.dto.ExceptionResponse;
import dev.abhisek.paymentservice.exceptions.models.InvalidCredentialException;
import dev.abhisek.paymentservice.exceptions.models.OrderNotFoundException;
import dev.abhisek.paymentservice.exceptions.models.PaymentNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static dev.abhisek.paymentservice.exceptions.models.BusinessError.*;

@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFoundException.class)
    public String handleException(PaymentNotFoundException e, Model model) {
        ExceptionResponse error = ExceptionResponse.builder()
                .code(PAYMENT_NOT_FOUND.getCode())
                .status(PAYMENT_NOT_FOUND.getStatus().toString())
                .messages(List.of(e.getLocalizedMessage()))
                .details(PAYMENT_NOT_FOUND.getDescription())
                .build();
        model.addAttribute("error", error);
        return "error-page";
    }

    @ExceptionHandler(InvalidCredentialException.class)
    public String handleException(InvalidCredentialException e, Model model, HttpServletRequest request) {
        ExceptionResponse error = ExceptionResponse.builder()
                .code(INVALID_CREDENTIALS.getCode())
                .status(INVALID_CREDENTIALS.getStatus().toString())
                .messages(List.of(e.getLocalizedMessage()))
                .details(INVALID_CREDENTIALS.getDescription())
                .build();
        model.addAttribute("error", error);
        return "error-page";
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public String handleException(UnsupportedOperationException e, Model model) {
        ExceptionResponse error = ExceptionResponse.builder()
                .code(UNSUPPORTED_OPERATION.getCode())
                .status(UNSUPPORTED_OPERATION.getStatus().toString())
                .messages(List.of(e.getLocalizedMessage()))
                .details(UNSUPPORTED_OPERATION.getDescription())
                .build();
        model.addAttribute("error", error);
        return "error-page";
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public String handleException(OrderNotFoundException e, Model model) {
        ExceptionResponse error = ExceptionResponse.builder()
                .code(ORDER_NOT_FOUND.getCode())
                .status(ORDER_NOT_FOUND.getStatus().toString())
                .messages(List.of(e.getLocalizedMessage()))
                .details(ORDER_NOT_FOUND.getDescription())
                .build();
        model.addAttribute("error", error);
        return "error-page";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        e.printStackTrace();
        ExceptionResponse error = ExceptionResponse.builder()
                .code(SERVER_ERROR.getCode())
                .status(SERVER_ERROR.getStatus().toString())
                .messages(List.of(e.getLocalizedMessage()))
                .details(SERVER_ERROR.getDescription())
                .build();
        model.addAttribute("error", error);
        return "error-page";
    }

}
