package dev.abhisek.orderservice.carts.services;

import dev.abhisek.orderservice.carts.dto.CartRequest;
import dev.abhisek.orderservice.carts.dto.CartResponse;

import java.util.List;

public interface CartServices {
    CartResponse createCart(CartRequest request, String userId);

    List<CartResponse> getAllUserCart(String userId);

    CartResponse getCartByProductId(String productId, String userId);

    void deleteProductCart(String productId, String userId);
}
