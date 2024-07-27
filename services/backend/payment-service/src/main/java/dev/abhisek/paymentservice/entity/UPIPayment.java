package dev.abhisek.paymentservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UPIPayment implements PaymentDetails {
    @Id
    private String id;
    private String upiId;
    private String transactionId;
}