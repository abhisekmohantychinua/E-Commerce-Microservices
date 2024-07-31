package dev.abhisek.paymentservice.entity;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardPayment implements PaymentDetails {
    private String id;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
    private String bankCode;// unique code to identify bank
    private String transactionId;
    private CardType cardType;
}
