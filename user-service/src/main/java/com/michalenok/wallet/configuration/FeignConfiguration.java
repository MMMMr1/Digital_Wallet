package com.michalenok.wallet.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
    @Bean
    public RequestInterceptor userIdRequestInterceptor(){
        return (template) -> {
            template.header("Is-Feign-Request", "true");
        };
    }
}