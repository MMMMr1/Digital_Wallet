package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.AccountInfoDto;
import com.michalenok.wallet.service.api.AccountService;
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

    @PostMapping
    protected ResponseEntity<?> create(@RequestParam(name = "uuid") UUID uuid) {
        log.info("create account for client [{}]", uuid);
        accountService.create(uuid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    protected Page<AccountInfoDto> getAll(Pageable pageable) {
        log.info("get all accounts");
        return accountService.getPage(pageable);
    }

    @GetMapping(path = "/details/{uuid}")
    public AccountInfoDto get(@PathVariable("uuid") UUID uuid) {
        log.info("get details for account [{}]", uuid);
        return accountService.findByAccountId(uuid);
    }

    @GetMapping(path = "/{uuid}")
    public List<AccountInfoDto> getAllAccountsByClientId(@PathVariable("uuid") UUID uuid) {
        log.info("get all accounts for client [{}]", uuid);
        return accountService.findAllByClientId(uuid);
    }
}