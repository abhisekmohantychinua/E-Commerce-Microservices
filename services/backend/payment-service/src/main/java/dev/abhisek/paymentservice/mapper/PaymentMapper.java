package dev.abhisek.paymentservice.mapper;

import dev.abhisek.paymentservice.dto.PaymentResponse;
import dev.abhisek.paymentservice.entity.Payment;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class PaymentMapper {
    public PaymentResponse fromPayment(Payment payment) {
        LocalDateTime completedTime = payment.getCompletedAt();
        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getType().toString(),
                payment.getCreatedAt().format(formatter()),
                completedTime == null ? "INCOMPLETE" : "COMPLETE",
                completedTime == null ? null : completedTime.format(formatter())
        );
    }

    private @NotNull DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }
}
