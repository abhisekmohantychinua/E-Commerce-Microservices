package dev.abhisek.reviewservice.service.external;

import dev.abhisek.reviewservice.dto.ProductResponse;
import dev.abhisek.reviewservice.dto.UserResponse;
import dev.abhisek.reviewservice.exceptions.models.ServiceDownException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
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

        ResponseEntity<UserResponse> responseEntity = null;
        try {
            responseEntity = template.exchange(
                    userUrl + "/profile",
                    GET,
                    entity,
                    UserResponse.class
            );
        } catch (ResourceAccessException e) {
            throw new ServiceDownException("user");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (responseEntity == null || responseEntity.getStatusCode().isError())
            return Optional.empty();

        if (responseEntity.getBody() == null)
            return Optional.empty();

        return Optional.of(responseEntity.getBody());
    }

    public Optional<ProductResponse> getProductByProductId(String productId) {
        ResponseEntity<ProductResponse> response = null;
        try {
            response = template.getForEntity(productUrl + "/" + productId, ProductResponse.class);
        } catch (ResourceAccessException e) {
            throw new ServiceDownException("product");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response == null || response.getStatusCode().isError())
            return Optional.empty();

        if (response.getBody() == null)
            return Optional.empty();

        return Optional.of(response.getBody());
    }
}
