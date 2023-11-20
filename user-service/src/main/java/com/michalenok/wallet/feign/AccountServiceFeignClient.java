package com.michalenok.wallet.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;

@FeignClient(name = "${feign.client.account-service.name}", fallbackFactory = AccountServiceFallback.class)
public interface AccountServiceFeignClient {

    @PostMapping("/api/v1/accounts")
    ResponseEntity<?> createAccount(@RequestParam(name = "client_uuid") UUID uuid);
}