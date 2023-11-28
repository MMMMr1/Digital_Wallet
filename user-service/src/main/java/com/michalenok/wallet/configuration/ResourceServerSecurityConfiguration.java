//package com.michalenok.wallet.configuration;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//
//@Configuration
//@EnableWebFluxSecurity
//public class ResourceServerSecurityConfiguration {
//
//    @Bean
//    public SecurityWebFilterChain configureResourceServer(ServerHttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchange -> exchange.pathMatchers("/actuator/health/**")
//                        .permitAll()
//                        .pathMatchers(HttpMethod.GET,"/webjars/**").permitAll()
//                        .pathMatchers(HttpMethod.GET,"/swagger-ui.html").permitAll()
//                        .pathMatchers(HttpMethod.GET,"/swagger-resources/**").permitAll()
//                        .pathMatchers(HttpMethod.GET,"/v3/api-docs/**").permitAll()
//                        .pathMatchers(HttpMethod.GET,"/user-service/v3/api-docs/**").permitAll()
//                        .pathMatchers(HttpMethod.GET,"/user-service/api/v1/users").permitAll()
//                        .anyExchange().authenticated()
//                ).oauth2ResourceServer((oauth) -> oauth
//                        .jwt(Customizer.withDefaults()))
//                .build();
//    }
//}