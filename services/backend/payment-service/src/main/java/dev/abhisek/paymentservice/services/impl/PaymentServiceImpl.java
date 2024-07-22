package dev.abhisek.paymentservice.services.impl;

import dev.abhisek.paymentservice.dto.PaymentRequest;
import dev.abhisek.paymentservice.dto.PaymentResponse;
import dev.abhisek.paymentservice.entity.Payment;
import dev.abhisek.paymentservice.entity.PaymentType;
import dev.abhisek.paymentservice.mapper.PaymentMapper;
import dev.abhisek.paymentservice.repo.PaymentRepository;
import dev.abhisek.paymentservice.services.PaymentService;
import dev.abhisek.paymentservice.services.external.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
                .amount(request.amount())
                .type(PaymentType.valueOf(request.paymentType()))
                .userId(request.userId())
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
    public void completePayment(String id, String userId) {
        orderService.completeOrder(id);
    }
}
