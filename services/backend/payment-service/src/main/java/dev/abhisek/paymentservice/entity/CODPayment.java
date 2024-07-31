package dev.abhisek.paymentservice.entity;

import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CODPayment implements PaymentDetails {
    private String id;
    private Status status;
    private String authorizationCode;
}
