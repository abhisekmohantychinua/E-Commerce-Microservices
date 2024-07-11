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

    private final String userService = "lb://user-service";
    private final String productService = "lb://product-service";
    private final String reviewService = "lb://review-service";
    private final String orderAndCartService = "lb://order-service";
    private final String paymentService = "lb://payment-service";
    private final String authorizationServer = "lb://authorization-server";

    @Bean
    RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // user-service
                .route(r -> r
                        .path("/users/**", "/users")
                        .filters(f -> f.filter(gatewayFilter()))
                        .uri(userService)
                )
                // product-service
                .route(r -> r
                        .path("/products/**")
                        .filters(f -> f.filter(gatewayFilter()))
                        .uri(productService))
                // review-service
                .route(r -> r
                        .path("/reviews/**")
                        .filters(f -> f.filter(gatewayFilter()))
                        .uri(reviewService))
                // order-service
                .route(r -> r
                        .path("/orders/**")
                        .filters(f -> f.filter(gatewayFilter()))
                        .uri(orderAndCartService)
                )
                .route(r -> r
                        .path("/carts/**")
                        .filters(f -> f.filter(gatewayFilter()))
                        .uri(orderAndCartService)
                )
                // payment-service
                .route(r -> r
                        .path("/payments/**")
                        .filters(f -> f.filter(gatewayFilter()))
                        .uri(paymentService)
                )
                // authorization-server
                .route(r -> r
                        .path("/oauth2/**", "/.well-known/**", "/logout")
                        .filters(f -> f.filter(gatewayFilter()))
                        .uri(authorizationServer)
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
