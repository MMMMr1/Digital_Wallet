package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.FallbackDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Log4j2
@RequestMapping("/fallback")
@RestController
public class FallbackController {

    @GetMapping("/userServiceCommonFallback")
    public ResponseEntity<Mono<FallbackDto>> userServiceCommonCircuitBreaker() {
        log.info("User service is down");
        return ResponseEntity.ok(Mono.just(FallbackDto.builder()
                .fallbackMessage("Currently user service is down. We are working to resolve the issue")
                .build()));
    }
}