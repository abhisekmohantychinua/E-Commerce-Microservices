package dev.abhisek.paymentservice.services.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", url = "${services.urls.order")
public interface OrderService {

    @PatchMapping("{id}/complete")
    void completeOrder(@PathVariable String id);
}
