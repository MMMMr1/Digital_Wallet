package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.service.api.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    protected ResponseEntity<?> create(@RequestBody @Validated UserCreateDto user) {
        log.info("create {}", user);
        service.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    protected Page<UserInfoDto> getAll(Pageable pageable) {
        return service.getPage(pageable);
    }

    @GetMapping(path = "/{uuid}")
    public UserInfoDto get(@RequestHeader(name = "Is-Proxy-Request", required = true) boolean isProxyRequest,
                           @PathVariable("uuid") UUID uuid) {
        log.info("get user with {}", uuid);
        return service.findById(uuid);
    }

    @PutMapping(path = "/{uuid}")
    public void update(@PathVariable("uuid") UUID uuid,
                       @Valid @RequestBody UserCreateDto user) {
        service.update(uuid, user);
        log.info("successfully update user with {}", uuid);
    }
}

