package dev.abhisek.paymentservice.services.impl;

import dev.abhisek.paymentservice.dto.OrderResponse;
import dev.abhisek.paymentservice.dto.PaymentRequest;
import dev.abhisek.paymentservice.dto.PaymentResponse;
import dev.abhisek.paymentservice.entity.Payment;
import dev.abhisek.paymentservice.entity.PaymentDetails;
import dev.abhisek.paymentservice.entity.PaymentType;
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

    private @NotNull DateTimeFormatter formatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }
}
