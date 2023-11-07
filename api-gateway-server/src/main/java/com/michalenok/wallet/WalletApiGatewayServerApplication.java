package com.michalenok.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@SpringBootApplication
public class WalletApiGatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletApiGatewayServerApplication.class, args);
	}
}