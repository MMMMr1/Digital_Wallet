package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.FallbackDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequestMapping("/fallback")
@RestController
public class FallbackController {

    @GetMapping("/user-service-common-fallback")
    public ResponseEntity<FallbackDto> userServiceCommonCircuitBreaker() {
        log.info("User service is down");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(FallbackDto.builder()
                .fallbackMessage("Currently user service is down. We are working to resolve the issue")
                .build());
    }

    @GetMapping("/account-service-common-fallback")
    public ResponseEntity<FallbackDto> accountServiceCommonCircuitBreaker() {
        log.info("User service is down");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(FallbackDto.builder()
                .fallbackMessage("Currently account service is down. We are working to resolve the issue")
                .build());
    }
}