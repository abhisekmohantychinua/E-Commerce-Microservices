package dev.abhisek.orderservice.order.repo;

import dev.abhisek.orderservice.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByUserId(String userId);

    Optional<Order> findByIdAndUserId(String id, String userId);
}
