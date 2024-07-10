package dev.abhisek.orderservice.order.services.external;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "payment-service", url = "${services.urls.payment}")
public interface PaymentService {
}
