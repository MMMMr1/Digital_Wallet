package com.michalenok.wallet.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "api-gateway-service")
public class ApiGatewayServiceConfigData {
    private Long timeoutMs;
    private Float failureRateThreshold;
    private Float slowCallRateThreshold;
    private Long slowCallDurationThreshold;
    private Integer permittedNumOfCallsInHalfOpenState;
    private Integer slidingWindowSize;
    private Integer minNumberOfCalls;
    private Long waitDurationInOpenState;
}