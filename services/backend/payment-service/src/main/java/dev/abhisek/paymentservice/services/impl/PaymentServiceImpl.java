package dev.abhisek.paymentservice.services.impl;

import dev.abhisek.paymentservice.dto.OrderResponse;
import dev.abhisek.paymentservice.dto.PaymentRequest;
import dev.abhisek.paymentservice.dto.PaymentResponse;
import dev.abhisek.paymentservice.dto.UserResponse;
import dev.abhisek.paymentservice.entity.*;
import dev.abhisek.paymentservice.exceptions.models.InvalidCredentialException;
import dev.abhisek.paymentservice.exceptions.models.OrderNotFoundException;
import dev.abhisek.paymentservice.exceptions.models.PaymentNotFoundException;
import dev.abhisek.paymentservice.exceptions.models.ServiceDownException;
import dev.abhisek.paymentservice.mapper.PaymentMapper;
import dev.abhisek.paymentservice.repo.PaymentRepository;
import dev.abhisek.paymentservice.services.PaymentService;
import dev.abhisek.paymentservice.services.external.OrderService;
import feign.RetryableException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static java.lang.Integer.parseInt;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final OrderService orderService;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        Payment payment = Payment.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(now().format(formatter()))
                .amount(request.amount())
                .type(PaymentType.valueOf(request.paymentType()))
                .userId(request.userId())
                .orderId(request.orderId())
                .build();
        payment = repository.save(payment);
        return mapper.fromPayment(payment);
    }

    @Override
    public PaymentResponse getPaymentById(String id, String userId) {
        return repository.findByIdAndUserId(id, userId)
                .map(mapper::fromPayment)
                .orElseThrow(() -> new PaymentNotFoundException("Requested payment entity not found on server with id: " + id));
    }

    @Override
    public List<PaymentResponse> getPaymentsByUserId(String userId) {
        return repository.findAllByUserId(userId)
                .stream().map(mapper::fromPayment)
                .toList();
    }

    @Override
    public PaymentResponse updatePaymentType(String paymentType, String id, String userId) {
        Payment payment = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new PaymentNotFoundException("Requested Payment is not found on server with id: " + id));

        if (payment.getCompletedAt() != null) {
            throw new UnsupportedOperationException("Payment already completed at " + payment.getCompletedAt() + ". Cannot update payment type.");
        }

        payment.setType(PaymentType.valueOf(paymentType));
        payment = repository.save(payment);
        return mapper.fromPayment(payment);
    }

    @Override
    public void deletePayment(String id) {
        repository.deleteById(id);
    }

    @Override
    public <T extends PaymentDetails> void completePayment(String id, String userId, T details) {
        Payment payment = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new PaymentNotFoundException("Requested payment not found on server with id: " + id));
        payment.setDetails(details);
        payment.setCompletedAt(now().format(formatter()));
        try {
            orderService.completeOrder(payment.getOrderId());
        } catch (RetryableException e) {
            throw new ServiceDownException("order");
        }
        repository.save(payment);
    }

    @Override
    public PaymentResponse updatePaymentAmount(String id, Double amount) {
        Payment payment = repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Requested payment not found on server with id: " + id));
        payment.setAmount(amount);
        payment = repository.save(payment);
        return mapper.fromPayment(payment);
    }

    @Override
    public OrderResponse getOrderByPaymentId(String id, String userId) {
        Payment payment = repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new PaymentNotFoundException("Requested payment not found on server with id: " + id));
        OrderResponse orderDetails;
        try {
            orderDetails = orderService.getOrderDetails(payment.getOrderId(), userId);
        } catch (RetryableException e) {
            throw new ServiceDownException("order");
        }
        if (orderDetails == null)
            throw new OrderNotFoundException("Requested order not found on server with id: " + payment.getOrderId());

        return orderDetails;
    }

    @Override
    public PaymentDetails validateCredentials(String id, UserResponse auth, String paymentType, String bankCode, String cardNumber, String cardHolderName, String expirationDate, String cvv, String cardType, String address, String city, String zip, String phone) {
        PaymentDetails details;
        if (paymentType.equalsIgnoreCase("upi")) {
            details = UPIPayment.builder()
                    .id(UUID.randomUUID().toString())
                    .upiId(auth.getId())
                    .transactionId(UUID.randomUUID().toString())
                    .build();
        } else if (paymentType.equalsIgnoreCase("net_banking")) {
            if (bankCode == null || bankCode.isBlank()) {
                throw new InvalidCredentialException("Valid Bank_code is not provided for Net banking.");
            }
            details = NetBankingPayment.builder()
                    .id(UUID.randomUUID().toString())
                    .bankCode(bankCode)
                    .ifscCode(bankCode + "ifsc")
                    .transactionId(UUID.randomUUID().toString())
                    .authorizationCode(UUID.randomUUID().toString())
                    .build();
        } else if (paymentType.equalsIgnoreCase("credit_card") || paymentType.equalsIgnoreCase("debit_card")) {
//            if (bankCode == null || bankCode.isBlank())
//                throw new InvalidCredentialException("Valid Bank_code is required for card payments.");

            if (cardHolderName == null || cardHolderName.isBlank())
                throw new InvalidCredentialException("Card holders name is required for card payments.");

            if (cardNumber == null || cardNumber.isBlank())
                throw new InvalidCredentialException("Card number is required field for card payment");

            if (expirationDate == null || expirationDate.isBlank())
                throw new InvalidCredentialException("Expiration date is a required credential for card payment.");
            else {
                if (expirationDate.length() != 5)
                    throw new InvalidCredentialException("Valid expiration date is required.");

                String expYearStr = "20" + expirationDate.substring(3);
                if (now().getYear() > parseInt(expYearStr))
                    throw new InvalidCredentialException("Provided card is expired " + expirationDate);

                String expMonthStr = expirationDate.substring(0, 2);
                if (now().getYear() == parseInt(expYearStr) && now().getMonthValue() > parseInt(expMonthStr))
                    throw new InvalidCredentialException("Provided card is expired " + expirationDate);

            }

            if (cvv == null || cvv.isBlank() || cvv.length() != 3)
                throw new InvalidCredentialException("Valid cvv is required credential for card payment.");

            if (cardType == null || cardType.isBlank())
                throw new InvalidCredentialException("Card type is a required field for card payment.");

            try {
                CardType.valueOf(cardType);
            } catch (IllegalArgumentException ignored) {
                throw new InvalidCredentialException("Provide a valid card type, i.e. CREDIT_CARD, DEBIT_CARD");
            }

            details = CardPayment.builder()
                    .id(UUID.randomUUID().toString())
                    .bankCode(bankCode)
                    .cardHolderName(cardHolderName)
                    .cardNumber(cardNumber)
                    .expirationDate(expirationDate)
                    .cvv(cvv)
                    .cardType(CardType.valueOf(cardType))
                    .transactionId(UUID.randomUUID().toString())
                    .build();
        } else if (paymentType.equalsIgnoreCase("cash_on_delivery")) {
            details = CODPayment.builder()
                    .id(UUID.randomUUID().toString())
                    .authorizationCode(UUID.randomUUID().toString())
                    .status(Status.INCOMPLETE)
                    .build();
        } else {
            throw new UnsupportedOperationException("This payment type is not available on our server. " + paymentType);
        }
        return details;
    }

    private @NotNull DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }
}
