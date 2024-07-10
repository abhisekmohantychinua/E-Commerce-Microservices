package dev.abhisek.orderservice.order.services.external;

import dev.abhisek.orderservice.order.dto.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "user-service", url = "${services.urls.user}")
public interface UserService {

    @GetMapping("address/{id}")
    AddressResponse getAddressById(@PathVariable String id, @RequestHeader("user_id") String userId);

    @GetMapping("address")
    List<AddressResponse> getAllUserAddress(@RequestHeader("user_id") String userId);
}
