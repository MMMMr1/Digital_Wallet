//package com.michalenok.wallet.configuration;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
//import org.springframework.security.oauth2.core.OAuth2TokenValidator;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtValidators;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.client.RestOperations;
//
//import java.time.Duration;
//
//import static org.springframework.security.config.Customizer.*;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class ResourceServerSecurityConfiguration {
////    private JwtAuthConverter jwtAuthConverter;
//    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
//    private String jwkSetUri;
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorization ->
//                        authorization
//                                .requestMatchers("user-service/v3/api-docs").permitAll()
////                                .requestMatchers("internal/**").permitAll().
//                                .requestMatchers(HttpMethod.GET,"user-service/api/v1/users/verification").permitAll()
//                                .requestMatchers(HttpMethod.POST,"user-service/api/v1/users/registration").permitAll()
//                                .requestMatchers(HttpMethod.GET, "user-service/api/v1/users").hasAuthority("ROLE_admin")
//                                .anyRequest().authenticated())
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));
//        return http.build();
//    }
//    @Bean
//    JwtDecoder jwtDecoder(RestTemplateBuilder builder) {
//        RestOperations rest = builder
//                .setConnectTimeout(Duration.ofSeconds(60))
//                .setReadTimeout(Duration.ofSeconds(60))
//                .build();
//
//        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).restOperations(rest).build();
//    }
////    @Bean
////    public AccessDeniedHandler accessDeniedHandler(){
////        return new CustomAccessDeniedHandler();
////    }
////    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
////    private String jwkSetUri;
////
////
////    @Bean
////    public JwtDecoder jwtDecoder() {
////        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri)
////                .build();
////    }
//}
