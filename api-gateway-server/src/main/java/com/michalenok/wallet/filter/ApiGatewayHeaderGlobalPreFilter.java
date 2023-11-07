package com.michalenok.wallet.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ApiGatewayHeaderGlobalPreFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header("Is-Proxy-Request", "true")
                .build();
        ServerWebExchange mutatedExchange = exchange.mutate().request(request).build();
        return chain.filter(mutatedExchange);
    }
}