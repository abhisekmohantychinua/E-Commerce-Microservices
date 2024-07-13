package dev.abhisek.authorizationserver.config;

import dev.abhisek.authorizationserver.client.ClientMapper;
import dev.abhisek.authorizationserver.client.ClientRepository;
import dev.abhisek.authorizationserver.user.UserResponse;
import dev.abhisek.authorizationserver.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Bean
    @Order(1)
    SecurityFilterChain asSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        http
                .exceptionHandling(e -> e.defaultAuthenticationEntryPointFor(
                        new LoginUrlAuthenticationEntryPoint("/oauth2/sign-in"),
                        new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                ));

        return http.build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(req -> req
                        .requestMatchers("output.css", "logo.png").permitAll()
                        .anyRequest().authenticated())
                .formLogin(f -> f.loginPage("/oauth2/sign-in")
                        .loginProcessingUrl("/oauth2/login")
                        .permitAll())
                .logout(l -> l.logoutUrl("/oauth2/logout")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userService.getUserByUsername(username)
                .orElseThrow(RuntimeException::new);// todo- handle exception properly
    }

    @Bean
    RegisteredClientRepository registeredClientRepository() {
        return new RegisteredClientRepository() {
            @Override
            public void save(RegisteredClient registeredClient) {
                clientRepository.save(clientMapper.toClient(registeredClient));
            }

            @Override
            public RegisteredClient findById(String id) {
                return clientRepository.findById(id)
                        .map(clientMapper::toRegisteredClient)
                        .orElseThrow();// todo- handle exception properly
            }

            @Override
            public RegisteredClient findByClientId(String clientId) {
                return clientRepository.findClientByClientId(clientId)
                        .map(clientMapper::toRegisteredClient)
                        .orElseThrow();// todo- handle exception properly
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();// todo- bcrypt password encoder
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    OAuth2TokenCustomizer<OAuth2TokenClaimsContext> tokenCustomizer() {
        return context -> {
            UserResponse userResponse = userService.getUserByUsername(context.getPrincipal().getName()).get();
            context.getClaims().subject(userResponse.getId());
            context.getClaims().claim(
                    "role",
                    userResponse
                            .getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList()
            );
        };
    }
}
