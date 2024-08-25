package dev.abhisek.authorizationserver.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RestTemplate template = new RestTemplate();


    @Value("${services.urls.user}")
    private String userUrl;

    public Optional<UserResponse> getUserByUsername(String username) {
        try {
            ResponseEntity<UserResponse> userResponseResponseEntity = template
                    .getForEntity(userUrl + "/auth?username={username}", UserResponse.class, Map.of("username", username));
            if (userResponseResponseEntity.getStatusCode().isError()) {
                return Optional.empty();
            }
            UserResponse userResponse = userResponseResponseEntity.getBody();
            if (userResponse == null) {
                return Optional.empty();
            }
            return Optional.of(userResponse);
        } catch (Exception e) {
            // todo - logging
        }
        return Optional.empty();
    }
}
