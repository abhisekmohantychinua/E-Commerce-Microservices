package dev.abhisek.apigateway.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusReactiveOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${security.config.introspectionUri}")
    private String introspectionUri;
    @Value("${security.config.clientId}")
    private String clientId;
    @Value("${security.config.secret}")
    private String secret;


    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .oauth2ResourceServer(r -> r
                        .opaqueToken(o -> o
                                .introspector(introspector())
                        ));

        http.authorizeExchange(e -> e
                // user-service
                .pathMatchers(POST, "/users").permitAll()
                .pathMatchers("/users/**").authenticated()
                // product-service
                .pathMatchers(POST, "/products").hasAuthority("ADMIN")
                .pathMatchers(PUT, "/products/**").hasAuthority("ADMIN")
                .pathMatchers(DELETE, "products/**").hasAuthority("ADMIN")
                .pathMatchers(GET, "products/**").permitAll()
                // review-service
                .pathMatchers(GET, "/reviews/products/**").permitAll()
                .pathMatchers("/reviews/**").authenticated()
                // order-service
                .pathMatchers("/orders/**", "/carts/**").authenticated()
                // payment-service
                .pathMatchers("/payments/**").authenticated()
                // authorization-server
                .pathMatchers("/oauth2/**", "/.well-known/**", "/logout").permitAll()
                .anyExchange().authenticated());

        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }

    @Bean
    ReactiveOpaqueTokenIntrospector introspector() {
        return token -> {
            ReactiveOpaqueTokenIntrospector delegate = new NimbusReactiveOpaqueTokenIntrospector(introspectionUri, clientId, secret);
            Mono<OAuth2AuthenticatedPrincipal> principal = delegate.introspect(token);
            return principal
                    .map(p -> new DefaultOAuth2AuthenticatedPrincipal(
                            p.getName(),
                            p.getAttributes(),
                            extractAuthorities(p)
                    ));
        };
    }

    private Collection<GrantedAuthority> extractAuthorities(OAuth2AuthenticatedPrincipal principal) {
        List<String> role = principal.getAttribute("role");
        return role.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
