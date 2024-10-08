package dev.abhisek.orderservice.order.controller;

import dev.abhisek.orderservice.order.dto.OrderRequest;
import dev.abhisek.orderservice.order.dto.OrderResponse;
import dev.abhisek.orderservice.order.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody @Valid OrderRequest request, @RequestHeader("user_id") String userId) {
        return ResponseEntity.ok(service.createOrder(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllUserOrder(@RequestHeader("user_id") String userId) {
        return ResponseEntity.ok(service.getAllUserOrder(userId));
    }

    @GetMapping("{id}")
    public ResponseEntity<OrderResponse> getUserOrderById(@PathVariable String id, @RequestHeader("user_id") String userId) {
        return ResponseEntity.ok(service.getOrderById(id, userId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> cancelOrder(@RequestHeader("user_id") String userId, @PathVariable String id) {
        service.cancelOrder(id, userId);
        return ResponseEntity.noContent().build();
    }

//    @PutMapping("{id}")
//    public ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderRequest request, @PathVariable String id, @RequestHeader("user_id") String userId) {
//        return ResponseEntity.ok(service.updateOrder(request, id, userId));
//    }

    @PatchMapping("{id}/complete")
    public ResponseEntity<Void> completeOrder(@PathVariable String id) {
        service.completeOrder(id);
        return ResponseEntity.noContent().build();
    }
}
