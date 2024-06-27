package dev.abhisek.orderservice.carts.repo;

import dev.abhisek.orderservice.carts.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findCartByProductIdAndUserId(String productId, String userId);

    List<Cart> findAllByUserId(String userId);

    void deleteByProductIdAndUserId(String productId, String userId);
}
