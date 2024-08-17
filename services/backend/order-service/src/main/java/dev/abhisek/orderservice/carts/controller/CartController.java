package dev.abhisek.orderservice.carts.controller;

import dev.abhisek.orderservice.carts.dto.CartRequest;
import dev.abhisek.orderservice.carts.dto.CartResponse;
import dev.abhisek.orderservice.carts.services.CartServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartServices services;

    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody @Valid CartRequest request, @RequestHeader("user_id") String userId) {
        return ResponseEntity.ok(services.createCart(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllUserCart(@RequestHeader("user_id") String userId) {
        return ResponseEntity.ok(services.getAllUserCart(userId));
    }

    @GetMapping("products/{productId}")
    public ResponseEntity<CartResponse> getProductCart(@PathVariable String productId, @RequestHeader("user_id") String userId) {
        return ResponseEntity.ok(services.getCartByProductId(productId, userId));
    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable String productId, @RequestHeader("user_id") String userId) {
        services.deleteProductCart(productId, userId);
        return ResponseEntity.noContent().build();
    }
}
