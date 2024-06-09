package dev.abhisek.authorizationserver.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final RestTemplate template = new RestTemplate();

    //    todo- to be added in application.yaml
    @Value("${user.url}")
    private String userUrl;

    public Optional<UserDetails> getUserByUsername(String username) {
        ResponseEntity<UserResponse> userResponseResponseEntity = template.getForEntity(userUrl, UserResponse.class, Map.of("username", username));


        if (userResponseResponseEntity.getStatusCode().isError()) {
            return Optional.empty();
        }
        UserResponse userResponse = userResponseResponseEntity.getBody();

        if (userResponse == null || userResponse.role() == null || userResponse.username() == null || userResponse.password() == null) {
            return Optional.empty();
        }
        UserDetails userDetails = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority(userResponse.role()));
            }

            @Override
            public String getPassword() {
                return userResponse.password();
            }

            @Override
            public String getUsername() {
                return userResponse.username();
            }
        };

        return Optional.of(userDetails);
    }
}
