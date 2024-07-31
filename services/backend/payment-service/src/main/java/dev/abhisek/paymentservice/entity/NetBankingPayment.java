package dev.abhisek.paymentservice.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NetBankingPayment implements PaymentDetails {
    private String id;
    private String bankCode;// unique code to identify bank
    private String ifscCode;
    private String transactionId;
    private String authorizationCode;
}
