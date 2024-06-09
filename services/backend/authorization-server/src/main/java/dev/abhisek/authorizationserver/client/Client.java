package dev.abhisek.authorizationserver.client;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.util.Set;

import static org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat.REFERENCE;

@Document
@Data
@Builder
public class Client {
    @Id
    private String id;
    private String clientId;
    private String clientSecret;
    private String clientName;
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
    private Set<AuthorizationGrantType> authorizationGrantTypes;
    private Set<String> redirectUris;
    private Set<String> scopes;

    public TokenSettings getTokenSettings() {
        return TokenSettings.builder()
                .accessTokenFormat(REFERENCE)
                .refreshTokenTimeToLive(Duration.ofDays(1))
                .build();
    }
}
