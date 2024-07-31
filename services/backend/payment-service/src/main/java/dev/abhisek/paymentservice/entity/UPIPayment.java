package dev.abhisek.paymentservice.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UPIPayment implements PaymentDetails {
    private String id;
    private String upiId;
    private String transactionId;
}
