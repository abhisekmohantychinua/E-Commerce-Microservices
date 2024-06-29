package dev.abhisek.orderservice.order.services.external;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", url = "${services.urls.user}")
public interface UserService {

}
