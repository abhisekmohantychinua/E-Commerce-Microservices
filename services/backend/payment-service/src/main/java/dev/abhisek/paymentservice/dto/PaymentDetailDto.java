package dev.abhisek.paymentservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.abhisek.paymentservice.entity.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter(AccessLevel.PRIVATE)
@Getter(AccessLevel.PUBLIC)
public class PaymentDetailDto {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String cvv;
    private String bankCode;// unique code to identify bank
    private String transactionId;
    private CardType cardType;
    private Status status;
    private String ifscCode;
    private String upiId;

    private PaymentDetailDto() {
    }

    public static <P extends PaymentDetails> PaymentDetailDto getPaymentDetail(P payment) {
        PaymentDetailDto details = new PaymentDetailDto();
        if (payment instanceof UPIPayment) {
            details.setUpiId(((UPIPayment) payment).getUpiId());
            details.setTransactionId(((UPIPayment) payment).getTransactionId());
        } else if (payment instanceof CardPayment) {
            details.setCardNumber(((CardPayment) payment).getCardNumber());
            details.setCardHolderName(((CardPayment) payment).getCardHolderName());
            details.setExpirationDate(((CardPayment) payment).getExpirationDate());
            details.setCvv(((CardPayment) payment).getCvv());
            details.setBankCode(((CardPayment) payment).getBankCode());
            details.setCardType(((CardPayment) payment).getCardType());
            details.setTransactionId(((CardPayment) payment).getTransactionId());
        } else if (payment instanceof CODPayment) {
            details.setStatus(((CODPayment) payment).getStatus());
        } else if (payment instanceof NetBankingPayment) {
            details.setIfscCode(((NetBankingPayment) payment).getIfscCode());
            details.setBankCode(((NetBankingPayment) payment).getBankCode());
            details.setTransactionId(((NetBankingPayment) payment).getTransactionId());
        } else {
            return null;
        }
        return details;
    }
}
