package com.michalenok.wallet.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@OpenAPIDefinition
@Configuration
@RequiredArgsConstructor
public class ApiGatewayConfiguration {

    private final ApiGatewayServiceConfigData gatewayServiceConfigData;

    @Bean
    Customizer<ReactiveResilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer() {
        return reactiveResilience4JCircuitBreakerFactory ->
                reactiveResilience4JCircuitBreakerFactory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                        .timeLimiterConfig(TimeLimiterConfig.custom()
                                .timeoutDuration(Duration.ofMillis(gatewayServiceConfigData.getTimeoutMs()))
                                .build())
                        .circuitBreakerConfig(CircuitBreakerConfig.custom()
                                .failureRateThreshold(gatewayServiceConfigData.getFailureRateThreshold())
                                .slowCallRateThreshold(gatewayServiceConfigData.getSlowCallRateThreshold())
                                .slowCallDurationThreshold(Duration.ofMillis(gatewayServiceConfigData.getSlowCallDurationThreshold()))
                                .permittedNumberOfCallsInHalfOpenState(gatewayServiceConfigData.getPermittedNumOfCallsInHalfOpenState())
                                .slidingWindowSize(gatewayServiceConfigData.getSlidingWindowSize())
                                .minimumNumberOfCalls(gatewayServiceConfigData.getMinNumberOfCalls())
                                .waitDurationInOpenState(Duration.ofMillis(gatewayServiceConfigData.getWaitDurationInOpenState()))
                                .build())
                        .build());
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("user_service", r -> r.path(
                                "/api/v1/users/{segment}",
                                "/api/v1/users",
                                "/user-service/v3/api-docs")
                        .filters(f -> f.addRequestHeader("Is-Proxy-Request", "true")
                                .circuitBreaker(c -> c.setName("userServiceCommonCircuitBreaker")
                                .setFallbackUri("forward:/fallback/user-service-common-fallback")))
                        .uri("lb://user-service"))
                .route("account_service", r -> r.path(
                                "/api/v1/accounts/{segment}",
                                "/api/v1/accounts",
                                "/api/v1/transfers/{segment}",
                                "/account-service/v3/api-docs")
                        .filters(f -> f.addRequestHeader("Is-Proxy-Request", "true")
                                .circuitBreaker(c -> c.setName("accountServiceCommonCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/account-service-common-fallback")))
                        .uri("lb://account-service"))
                .route("money-transfer-service", r -> r.path(
                                "/api/v1/money_transfers/{segment}",
                                "/api/v1/money_transfers",
                                "/money-transfer-service/v3/api-docs")
                        .filters(f -> f.addRequestHeader("Is-Proxy-Request", "true")
                                .circuitBreaker(c -> c.setName("accountServiceCommonCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/account-service-common-fallback")))
                        .uri("lb://money-transfer-service"))
                .build();
    }
}