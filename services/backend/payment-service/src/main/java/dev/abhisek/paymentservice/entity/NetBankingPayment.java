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
public class NetBankingPayment implements PaymentDetails {
    @Id
    private String id;
    private String bankCode;// unique code to identify bank
    private String ifscCode;
    private String transactionId;
    private String authorizationCode;
}
