package dev.abhisek.orderservice.order.repo;

import dev.abhisek.orderservice.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByUserId(String userId);
}
