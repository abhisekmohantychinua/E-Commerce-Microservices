package dev.abhisek.paymentservice.services.external;

import dev.abhisek.paymentservice.dto.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "order-service", url = "${services.urls.order}")
public interface OrderService {

    @PatchMapping("{id}/complete")
    void completeOrder(@PathVariable String id);

    @GetMapping("{id}")
    OrderResponse getOrderDetails(@PathVariable String id, @RequestHeader("user_id") String userId);
}
