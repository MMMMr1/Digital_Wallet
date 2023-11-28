package com.michalenok.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@RefreshScope
@SpringBootApplication
public class WalletApiGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletApiGatewayServerApplication.class, args);
	}
//	@Bean
//	public CorsWebFilter corsWebFilter() {
//		final CorsConfiguration corsConfig = new CorsConfiguration();
//		corsConfig.setAllowedOrigins(Collections.singletonList("http://localhost:8072"));
//		corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "HEAD", "PUT"));
//		corsConfig.addAllowedHeader("Access-Control-Allow-Origin");
//
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", corsConfig);
//
//		return new CorsWebFilter(source);
//	}
}