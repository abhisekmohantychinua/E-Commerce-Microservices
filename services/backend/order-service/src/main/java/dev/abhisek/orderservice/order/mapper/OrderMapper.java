package dev.abhisek.orderservice.order.mapper;

import dev.abhisek.orderservice.order.dto.*;
import dev.abhisek.orderservice.order.entity.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderMapper {
    public OrderResponse fromOrder(Order order, AddressResponse address, PaymentResponse payment, List<OrderLineResponse> orderLines) {
        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getUserId(),
                address,
                payment,
                order.getCreatedAt().toString(),
                orderLines
        );
    }

    public List<OrderResponse> fromOrders(List<Order> orders, List<AddressResponse> allUserAddress, List<PaymentResponse> allPayments, List<ProductResponse> allProducts) {
        List<OrderResponse> orderResponses = new ArrayList<>();

        for (Order order : orders) {
            OrderResponse orderResponse = fromOrder(
                    order,
                    allUserAddress.stream().filter(a -> a.id().equals(order.getAddressId())).findFirst().get(),
                    allPayments.stream().filter(p -> p.id().equals(order.getPaymentId())).findFirst().get(),
                    order.getOrderLines().stream()
                            .map(orderLine -> new OrderLineResponse(
                                    allProducts.stream().filter(p -> p.id().equals(orderLine.getProductId())).findFirst().get(),
                                    orderLine.getQuantity()
                            ))
                            .toList()
            );
            orderResponses.add(orderResponse);
        }
        return orderResponses;
    }
}
