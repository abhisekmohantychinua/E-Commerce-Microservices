package dev.abhisek.paymentservice.services;

import dev.abhisek.paymentservice.dto.PaymentRequest;
import dev.abhisek.paymentservice.dto.PaymentResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPaymentById(String id, String userId);

    List<PaymentResponse> getPaymentsByUserId(String userId);

    PaymentResponse updatePaymentType(String paymentType, String id, String userId);

    void deletePayment(String id);

    void completePayment(String id, String userId);

    PaymentResponse updatePaymentAmount(String id, Double amount);
}
