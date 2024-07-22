package dev.abhisek.orderservice.order.services;

import dev.abhisek.orderservice.order.dto.OrderRequest;
import dev.abhisek.orderservice.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request, String userId);

    List<OrderResponse> getAllUserOrder(String userId);

    void cancelOrder(String id, String userId);

    void completeOrder(String id);

    OrderResponse getOrderById(String id, String userId);

//    OrderResponse updateOrder(OrderRequest request, String id, String userId);
}
