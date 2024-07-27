package dev.abhisek.paymentservice.security;

import dev.abhisek.paymentservice.services.external.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests(r -> r.requestMatchers("/payments/api/**", "/output.css", "/**.png", "/**.jpeg").permitAll()
                        .anyRequest().authenticated()
                );

        http
                .formLogin(l -> l.loginPage("/payments/signin")
                        .loginProcessingUrl("/payments/login")
                        .defaultSuccessUrl("/payments/dashboard")
                        .permitAll())
                .logout(l -> l.logoutUrl("/payments/logout")
                        .permitAll());
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return userService::getUserByUsername;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
