package dev.abhisek.paymentservice.services.impl;

import dev.abhisek.paymentservice.dto.OrderResponse;
import dev.abhisek.paymentservice.dto.PaymentRequest;
import dev.abhisek.paymentservice.dto.PaymentResponse;
import dev.abhisek.paymentservice.dto.UserResponse;
import dev.abhisek.paymentservice.entity.*;
import dev.abhisek.paymentservice.mapper.PaymentMapper;
import dev.abhisek.paymentservice.repo.PaymentRepository;
import dev.abhisek.paymentservice.services.PaymentService;
import dev.abhisek.paymentservice.services.external.OrderService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

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
                .orElseThrow();// todo - exception
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
                .orElseThrow();// todo - exception

        if (payment.getCompletedAt() != null) {
            throw new RuntimeException();// todo - exception
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
        Payment payment = repository.findByIdAndUserId(id, userId).orElseThrow();//todo -exception
        payment.setDetails(details);
        payment.setCompletedAt(now().format(formatter()));
        orderService.completeOrder(payment.getOrderId());
        repository.save(payment);
    }

    @Override
    public PaymentResponse updatePaymentAmount(String id, Double amount) {
        Payment payment = repository.findById(id)
                .orElseThrow();// todo - exception
        payment.setAmount(amount);
        payment = repository.save(payment);
        return mapper.fromPayment(payment);
    }

    @Override
    public OrderResponse getOrderByPaymentId(String id, String userId) {
        Payment payment = repository.findByIdAndUserId(id, userId).orElseThrow();//todo -exception
        return orderService.getOrderDetails(payment.getOrderId(), userId);
    }


    // todo - multiple exception
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
            details = NetBankingPayment.builder()
                    .id(UUID.randomUUID().toString())
                    .bankCode(bankCode)
                    .ifscCode(bankCode + "ifsc")
                    .transactionId(UUID.randomUUID().toString())
                    .authorizationCode(UUID.randomUUID().toString())
                    .build();
        } else if (paymentType.equalsIgnoreCase("credit_card") || paymentType.equalsIgnoreCase("debit_card")) {
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
            throw new RuntimeException();// todo - exceptions
        }
        return details;
    }

    private @NotNull DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }
}
