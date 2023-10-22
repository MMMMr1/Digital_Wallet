package com.michalenok.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class WalletUserServiceRunner {
    public static void main(String[] args) {
        SpringApplication.run(WalletUserServiceRunner.class, args);
    }
}
