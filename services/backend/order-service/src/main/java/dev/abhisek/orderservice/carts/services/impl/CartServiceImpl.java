package dev.abhisek.orderservice.carts.services.impl;

import dev.abhisek.orderservice.carts.dto.CartRequest;
import dev.abhisek.orderservice.carts.dto.CartResponse;
import dev.abhisek.orderservice.carts.dto.ProductResponse;
import dev.abhisek.orderservice.carts.entity.Cart;
import dev.abhisek.orderservice.carts.repo.CartRepository;
import dev.abhisek.orderservice.carts.services.CartServices;
import dev.abhisek.orderservice.order.services.external.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartServices {
    private final CartRepository repository;

    private final ProductService productService;

    @Override
    public CartResponse createCart(CartRequest request, String userId) {
        ProductResponse productResponse = productService.findProductByIdForCarts(request.productId());
        Cart cart = repository.findCartByProductIdAndUserId(request.productId(), userId)
                .orElse(new Cart());
        cart.setProductId(productResponse.id());
        cart.setUserId(userId);
        cart.setQuantity(request.quantity());
        cart = repository.save(cart);
        return new CartResponse(productResponse, cart.getQuantity());
    }

    @Override
    public List<CartResponse> getAllUserCart(String userId) {
        return repository.findAllByUserId(userId)
                .stream().map(c -> {
                    ProductResponse response = productService.findProductByIdForCarts(c.getProductId());
                    return new CartResponse(response, c.getQuantity());
                })
                .toList();
    }

    @Override
    public CartResponse getCartByProductId(String productId, String userId) {
        Cart cart = repository.findCartByProductIdAndUserId(productId, userId)
                .orElseThrow();// todo - exception
        ProductResponse response = productService.findProductByIdForCarts(cart.getProductId());
        return new CartResponse(response, cart.getQuantity());
    }

    @Transactional
    @Override
    public void deleteProductCart(String productId, String userId) {
        repository.deleteByProductIdAndUserId(productId, userId);
    }


}
