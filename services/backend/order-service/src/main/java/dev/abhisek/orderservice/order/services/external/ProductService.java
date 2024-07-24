package dev.abhisek.orderservice.order.services.external;


import dev.abhisek.orderservice.order.dto.ProductPatchRequest;
import dev.abhisek.orderservice.order.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", url = "${services.urls.product}")
public interface ProductService {
    @GetMapping("{id}")
    dev.abhisek.orderservice.carts.dto.ProductResponse findProductByIdForCarts(@PathVariable String id);

    @GetMapping
    List<ProductResponse> findProductByIdForOrder(@RequestParam List<String> productIds);

    @PatchMapping
    void patchProductQuantity(@RequestBody List<ProductPatchRequest> requests);
}
