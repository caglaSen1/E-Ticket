package com.ftbootcamp.eticketgw.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@EnableMethodSecurity
@EnableWebSecurity
@EnableWebFluxSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    private static final String[] AUTH_WHITELIST = {
            "/api/v1/users/login",
            "/api/v1/users/register"
    };

    private static final String[] AUTH_WHITELIST2 = {
            "/api/v1/auth/login","api/v1/auth/register"
    };

    private static final String[] AUTH_WHITELIST_USER = {
            "/api/v1/searches/searchByCityAndDepartureDate",
    };
    private static final String[] AUTH_WHITELIST_ADMIN = {

    };

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchangeSpec ->
                        exchangeSpec

                                .pathMatchers("/api/v1/users/{dynamicSegment}/create").denyAll()
                                .pathMatchers("/api/v1/users/{dynamicSegment}/admin-panel/create").denyAll()

                                .pathMatchers("/api/v1/auth/**").permitAll()
                                .pathMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                                .pathMatchers("/api/v1/users/{dynamicSegment}/admin-panel/**").hasRole("ADMIN")
                                .pathMatchers("/api/v1/roles/admin-panel/**").hasRole("ADMIN")

                                .pathMatchers("/api/v1/users/company-users/{id}", "/api/v1/users/company-users/update",
                                        "/api/v1/users/company-users/change-password").hasRole("CORPORATE_USER")

                                .pathMatchers("/api/v1/users/admin-users/{id}", "/api/v1/users/admin-users/update",
                                        "/api/v1/users/admin-users/change-password").hasRole("INDIVIDUAL_USER")

                                .anyExchange().authenticated()
                )

                .addFilterBefore(jwtRequestFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance());
        return http.build();
    }
}
