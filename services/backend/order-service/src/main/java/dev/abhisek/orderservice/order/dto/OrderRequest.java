package dev.abhisek.orderservice.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record OrderRequest(
        @NotNull(message = "Address id is a required field.")
        @NotEmpty(message = "Address id cannot be empty.")
        String addressId,
        @NotNull(message = "Products are required field.")
        @NotEmpty(message = "Products cannot be empty.")
        List<ProductRequest> products,
        @NotNull(message = "Payment Method is a required field.")
        @NotEmpty(message = "Payment method cannot be empty.")
        @Pattern(regexp = "^(DEBIT_CARD|CREDIT_CARD|UPI|CASH_ON_DELIVERY|NET_BANKING)$", message = "Please provide a valid payment type.{ DEBIT_CARD, CREDIT_CARD, UPI, CASH_ON_DELIVERY, NET_BANKING }")
        String paymentMethod
) {
}
