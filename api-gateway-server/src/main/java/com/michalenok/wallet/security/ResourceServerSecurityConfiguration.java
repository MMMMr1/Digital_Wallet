package com.michalenok.wallet.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServerBearerExchangeFilterFunction;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;

@Log4j2
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
@RequiredArgsConstructor
public class ResourceServerSecurityConfiguration {
    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;
    private static final String[] AUTH_WHITELIST = {
            "/actuator/health/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/user-service/v3/api-docs/**"
    };
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authz -> authz
                        .pathMatchers(HttpMethod.GET, "api/v1/users").hasAuthority("ADMIN")
                        .pathMatchers(HttpMethod.POST, "api/v1/users").hasAuthority("ADMIN")
                        .pathMatchers(HttpMethod.GET, "api/v1/users/{uuid}").hasAuthority("ADMIN")
                        .pathMatchers(HttpMethod.POST, "api/v1/users/registration").permitAll()
                        .pathMatchers(HttpMethod.GET, "api/v1/users/verification").permitAll()
                        .pathMatchers(AUTH_WHITELIST).permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(grantedAuthoritiesExtractor());
        return http.build();
    }
    Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter
                (new GrantedAuthoritiesExtractor());
        jwtAuthenticationConverter.setPrincipalClaimName(StandardClaimNames.PREFERRED_USERNAME);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    @Bean
    MapReactiveUserDetailsService userDetailsService() {
        UserDetails userDetails = User.withUsername("admin").password("admin").roles("ADMIN").build();
        return new MapReactiveUserDetailsService(List.of(userDetails));
    }
    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri)
                .build();
    }
    @Bean
    public WebClient rest() {
        return WebClient.builder()
                .filter(new ServerBearerExchangeFilterFunction())
                .build();
    }
}