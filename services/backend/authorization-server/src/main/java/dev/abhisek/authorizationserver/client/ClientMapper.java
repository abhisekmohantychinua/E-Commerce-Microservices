package dev.abhisek.authorizationserver.client;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat.REFERENCE;

@Service
public class ClientMapper {
    public Client toClient(RegisteredClient registeredClient) {
        return Client.builder()
                .id(registeredClient.getId())
                .clientId(registeredClient.getClientId())
                .clientSecret(registeredClient.getClientSecret())
                .clientName(registeredClient.getClientName())
                .clientAuthenticationMethods(registeredClient.getClientAuthenticationMethods())
                .authorizationGrantTypes(registeredClient.getAuthorizationGrantTypes())
                .redirectUris(registeredClient.getRedirectUris())
                .scopes(registeredClient.getScopes())
                .build();
    }

    public RegisteredClient toRegisteredClient(Client client) {
        RegisteredClient registeredClient = RegisteredClient.withId(client.getId())
                .clientName(client.getClientName())
                .clientSecret(client.getClientSecret())
                .clientId(client.getClientId())
                .clientAuthenticationMethods(clientAuthenticationMethods -> clientAuthenticationMethods.addAll(client.getClientAuthenticationMethods()))
                .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes.addAll(client.getAuthorizationGrantTypes()))
                .redirectUris(redirectUris -> redirectUris.addAll(client.getRedirectUris()))
                .scopes(scopes -> scopes.addAll(client.getScopes()))
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(REFERENCE)
                        .accessTokenTimeToLive(Duration.ofDays(1))
                        .build())
                .build();


        return registeredClient;
    }
}
