package dev.abhisek.paymentservice.services;

import dev.abhisek.paymentservice.dto.OrderResponse;
import dev.abhisek.paymentservice.dto.PaymentRequest;
import dev.abhisek.paymentservice.dto.PaymentResponse;
import dev.abhisek.paymentservice.entity.PaymentDetails;

import java.util.List;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse getPaymentById(String id, String userId);

    List<PaymentResponse> getPaymentsByUserId(String userId);

    PaymentResponse updatePaymentType(String paymentType, String id, String userId);

    void deletePayment(String id);

    <T extends PaymentDetails> void completePayment(String id, String userId, T details);

    PaymentResponse updatePaymentAmount(String id, Double amount);


    OrderResponse getOrderByPaymentId(String id, String userId);
}
