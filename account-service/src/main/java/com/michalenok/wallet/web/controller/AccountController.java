package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.response.AccountInfoDto;
import com.michalenok.wallet.service.api.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    @Operation(summary = "Creating of new account", tags = "accounts")
    @PostMapping
    protected ResponseEntity<?> create(@RequestParam(name = "client_uuid") UUID uuid) {
        log.info("create account for client [{}]", uuid);
        accountService.create(uuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Loading of accounts", tags = "accounts")
    @GetMapping
    protected Page<AccountInfoDto> getAll(Pageable pageable) {
        log.info("get all accounts");
        return accountService.getAccounts(pageable);
    }

    @Operation(summary = "Loading of account details", tags = "accounts")
    @GetMapping(path = "/details")
    public AccountInfoDto get(@RequestParam("account_uuid") UUID uuid) {
        log.info("get details for account [{}]", uuid);
        return accountService.findByAccountId(uuid);
    }

    @Operation(summary = "Loading of all client accounts by client uuid", tags = "accounts")
    @GetMapping(path = "/{uuid}")
    public List<AccountInfoDto> getAllAccountsByClientId(@PathVariable("uuid") UUID uuid) {
        log.info("get all accounts for client [{}]", uuid);
        return accountService.findAllByClientId(uuid);
    }
    @Operation(summary = "Closing of account by uuid", tags = "accounts")
    @PutMapping(path = "/closing")
    public void closeAccount(@RequestParam("account_uuid") UUID uuid) {
        log.info("close account {}", uuid);
         accountService.close(uuid);
    }
}