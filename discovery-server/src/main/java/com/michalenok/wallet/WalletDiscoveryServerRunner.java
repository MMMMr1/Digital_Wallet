package com.michalenok.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class WalletDiscoveryServerRunner {
    public static void main(String[] args) {
        SpringApplication.run(WalletDiscoveryServerRunner.class, args);
    }
}