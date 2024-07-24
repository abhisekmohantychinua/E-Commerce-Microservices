package dev.abhisek.paymentservice.mapper;

import dev.abhisek.paymentservice.dto.PaymentResponse;
import dev.abhisek.paymentservice.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentResponse fromPayment(Payment payment) {
        String completedTime = payment.getCompletedAt();
        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getType().toString(),
                payment.getCreatedAt(),
                completedTime == null ? "INCOMPLETE" : "COMPLETE",
                completedTime
        );
    }
}
