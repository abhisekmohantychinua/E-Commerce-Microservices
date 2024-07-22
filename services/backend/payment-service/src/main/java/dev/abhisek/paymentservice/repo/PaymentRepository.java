package dev.abhisek.paymentservice.repo;

import dev.abhisek.paymentservice.entity.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    Optional<Payment> findByIdAndUserId(String id, String userId);

    List<Payment> findAllByUserId(String userId);
}
