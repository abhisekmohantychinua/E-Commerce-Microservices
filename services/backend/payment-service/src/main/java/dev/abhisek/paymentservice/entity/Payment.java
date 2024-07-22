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
    @CreatedDate
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private String userId;
}
