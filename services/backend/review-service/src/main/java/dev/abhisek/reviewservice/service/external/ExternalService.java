package dev.abhisek.reviewservice.service.external;

import dev.abhisek.reviewservice.dto.ProductResponse;
import dev.abhisek.reviewservice.dto.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;

@Service
public class ExternalService {
    private final RestTemplate template = new RestTemplate();

    @Value("${services.urls.user}")
    private String userUrl;
    @Value("${services.urls.product}")
    private String productUrl;


    public Optional<UserResponse> getUserByUseId(String userId) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("user_id", userId);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UserResponse> responseEntity = template.exchange(
                userUrl + "/profile",
                GET,
                entity,
                UserResponse.class
        );
        if (responseEntity.getStatusCode().isError())
            return Optional.empty();

        if (responseEntity.getBody() == null)
            return Optional.empty();

        return Optional.of(responseEntity.getBody());
    }

    public Optional<ProductResponse> getProductByProductId(String productId) {
        ResponseEntity<ProductResponse> response = template.getForEntity(productUrl + "/" + productId, ProductResponse.class);
        if (response.getStatusCode().isError())
            return Optional.empty();

        if (response.getBody() == null)
            return Optional.empty();

        return Optional.of(response.getBody());
    }
}
