package dev.abhisek.paymentservice.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Payment {
    @Id
    private String id;
    private Double amount;
    private PaymentType type;
    private String createdAt;
    private String completedAt;
    private String userId;
    @Indexed(unique = true)
    private String orderId;
}
