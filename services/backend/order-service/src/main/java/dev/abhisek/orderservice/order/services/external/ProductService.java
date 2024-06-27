package dev.abhisek.orderservice.order.services.external;

import dev.abhisek.orderservice.carts.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${services.urls.product}")
public interface ProductService {
    @GetMapping("{id}")
    ProductResponse findProductById(@PathVariable String id);
}
