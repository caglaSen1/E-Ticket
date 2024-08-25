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

    private static final String[] AUTH_WHITELIST_DENY_ALL = {
            "/api/v1/payments/process-and-send-queue"
    };

    private static final String[] AUTH_WHITELIST_PERMIT_ALL = {
            "/api/v1/auth/**",
            "/api/v1/email-templates", "/api/v1/emails",
            "/swagger-ui/**", "/v3/api-docs/**"
    };

    private static final String[] AUTH_WHITELIST_CORPORATE_USER = {
            "/api/v1/users/company-users/{id}", "/api/v1/users/company-users/update",
            "/api/v1/users/company-users/change-password"
    };
    private static final String[] AUTH_WHITELIST_ADMIN = {
            "/api/v1/users/{dynamicSegment}/admin-panel/**",
            "/api/v1/roles/admin-panel/**",
            "/api/v1/payments/admin-panel/**",
            "/api/v1/tickets/admin-panel/**",
            "/api/v1/trips/admin-panel/**"
    };
    private static final String[] AUTH_WHITELIST_INDIVIDUAL_USER = {
            "/api/v1/users/individual-users/{id}", "/api/v1/users/individual-users/update",
            "/api/v1/users/individual-users/change-password"
    };
    private static final String[] AUTH_WHITELIST_INDIVIDUAL_CORPORATE_USERS = {
            "/api/v1/tickets/buy", "/api/v1/tickets/buy-multiple",
            "/api/v1/tickets/buyer/all/{email}", "/api/v1/tickets/trips/{tripId}/all-available",
            "/api/v1/trips/all-available"
    };

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchangeSpec ->
                        exchangeSpec

                                .pathMatchers(AUTH_WHITELIST_DENY_ALL).denyAll()
                                .pathMatchers(AUTH_WHITELIST_PERMIT_ALL).permitAll()
                                .pathMatchers(AUTH_WHITELIST_ADMIN).hasRole("ADMIN")
                                .pathMatchers(AUTH_WHITELIST_CORPORATE_USER).hasRole("CORPORATE_USER")
                                .pathMatchers(AUTH_WHITELIST_INDIVIDUAL_USER).hasRole("INDIVIDUAL_USER")
                                .pathMatchers(AUTH_WHITELIST_INDIVIDUAL_CORPORATE_USERS)
                                .hasAnyRole("INDIVIDUAL_USER", "CORPORATE_USER")

                                .anyExchange().authenticated()
                )
                .addFilterBefore(jwtRequestFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance());
        return http.build();
    }
}
