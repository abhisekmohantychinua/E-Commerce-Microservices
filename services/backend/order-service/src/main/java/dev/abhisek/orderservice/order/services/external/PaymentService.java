package dev.abhisek.orderservice.order.services.external;

import dev.abhisek.orderservice.order.dto.PaymentRequest;
import dev.abhisek.orderservice.order.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "payment-service", url = "${services.urls.payment}")
public interface PaymentService {
    @PostMapping("api/create")
    PaymentResponse createPayment(@RequestBody PaymentRequest request);

    @GetMapping("api")
    List<PaymentResponse> getPaymentByUserId(@RequestHeader("user_id") String userId);

    @DeleteMapping("api/{id}")
    void deletePayment(@PathVariable String id);

    @GetMapping("api/{id}")
    PaymentResponse getPaymentById(@PathVariable String id, @RequestHeader("user_id") String userId);
}
