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
public class CardPayment implements PaymentDetails {
    @Id
    private String id;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
    private String bankCode;// unique code to identify bank
    private String transactionId;
    private CardType cardType;
}
