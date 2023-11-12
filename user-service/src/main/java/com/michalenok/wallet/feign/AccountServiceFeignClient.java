package com.michalenok.wallet.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;

@FeignClient("${feign.client.account-service.name}")
public interface AccountServiceFeignClient {

    @PostMapping("/api/v1/accounts")
    ResponseEntity<?> createDefaultAccount(@RequestParam(name = "client_uuid") UUID uuid);
}