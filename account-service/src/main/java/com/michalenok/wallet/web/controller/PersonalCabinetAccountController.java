package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.aspect.Logged;
import com.michalenok.wallet.model.dto.response.AccountInfoDto;
import com.michalenok.wallet.service.api.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "personal cabinet")
@Log4j2
@RestController
@RequestMapping("/api/v1/accounts/personal_details")
@RequiredArgsConstructor
public class PersonalCabinetAccountController {
    private final AccountService accountService;

    @Logged
    @Operation(summary = "Loading of all client accounts by client uuid", tags = "accounts")
    @GetMapping
    public List<AccountInfoDto> getAllAccounts(@AuthenticationPrincipal Jwt jwt) {
        log.info("get all accounts for client [{}]", jwt.getSubject());
        return accountService.findAllByClientId(UUID.fromString(jwt.getSubject()));
    }
}