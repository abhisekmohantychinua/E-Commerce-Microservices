package dev.abhisek.apigateway.routing;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;

@Configuration
public class RouteConfig {


    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/users/**")
                        .filters(f -> f.filter(gatewayFilter()))
                        .uri("http://localhost:8081")
                )
                .build();
    }

    @Bean
    GatewayFilter gatewayFilter() {
        return (exchange, chain) -> {
            return ReactiveSecurityContextHolder.getContext()
                    .map(SecurityContext::getAuthentication)
                    .map(Authentication::getName)
                    .flatMap(userId -> chain.filter(
                            exchange
                                    .mutate()
                                    .request(r -> r.headers(h -> h.add("user_id", userId)))
                                    .build()
                    ));
        };
    }
}
