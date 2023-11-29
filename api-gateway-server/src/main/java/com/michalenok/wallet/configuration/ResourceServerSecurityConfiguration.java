//package com.michalenok.wallet.configuration;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
//import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import java.util.List;
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
//@Configuration
//@RequiredArgsConstructor
//public class ResourceServerSecurityConfiguration {
//    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
//    private String jwkSetUri;
//
//    private static final String[] AUTH_WHITELIST = {
//            "/actuator/health/**",
//            "/webjars/**",
//            "/swagger-ui.html",
//            "/swagger-resources/**",
//            "/v3/api-docs/**",
//            "/user-service/v3/api-docs/**",
//            "*/user-service/api/v1/users/registration",
//            "*/user-service/api/v1/users/verification"
//    };
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    @Bean
//    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
//        http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .cors().disable()
//                .authorizeExchange((exchanges) -> exchanges
//                        .pathMatchers(AUTH_WHITELIST).permitAll()
//                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .anyExchange().authenticated()
//                )
//                .oauth2ResourceServer(oAuth2ResourceServerSpec -> {
//                    oAuth2ResourceServerSpec.jwt(withDefaults());
//                });
//        return http.build();
//    }
//
////    @Bean
////    SecurityWebFilterChain webHttpSecurity(ServerHttpSecurity http) {
////        http
////                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//////                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/user-service/**"))
////                .authorizeExchange((exchanges) -> exchanges
//////                        .pathMatchers("/user-service/v3/api-docs/**").permitAll()
////                        .pathMatchers("/user-service/api/v1/users/registration").permitAll()
////                        .pathMatchers("/user-service/api/v1/users/verification").permitAll()
////                        .anyExchange().authenticated()
////                )
////                .httpBasic(withDefaults());
////        return http.build();
////    }
//    @Bean
//    MapReactiveUserDetailsService userDetailsService() {
//        UserDetails userDetails = User.withUsername("admin").password("admin").roles("ADMIN").build();
//        return new MapReactiveUserDetailsService(List.of(userDetails));
//    }
////    @Bean
////    public SecurityWebFilterChain configureResourceServer(ServerHttpSecurity httpSecurity) throws Exception {
////        return httpSecurity
////                .csrf(ServerHttpSecurity.CsrfSpec::disable)
////                .authorizeExchange()
////                .pathMatchers(AUTH_WHITELIST).permitAll()
////                .anyExchange().authenticated()
////                .and().oauth2ResourceServer(oAuth2ResourceServerSpec -> {
////                    oAuth2ResourceServerSpec.jwt(withDefaults());
////                })
////                .build();
////    }
//
//    @Bean
//    public ReactiveJwtDecoder jwtDecoder() {
//        return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri)
//                .build();
//    }
//}