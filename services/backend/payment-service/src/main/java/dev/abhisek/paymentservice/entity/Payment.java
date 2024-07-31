package dev.abhisek.paymentservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


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
    private PaymentDetails details;
}
