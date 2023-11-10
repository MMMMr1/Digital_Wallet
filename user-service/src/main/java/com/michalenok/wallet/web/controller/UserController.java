package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.service.api.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "US1: Register client in application", tags = "users")
    @PostMapping
    protected ResponseEntity<?> create(@RequestBody @Validated UserCreateDto user) {
        log.info("create {}", user);
        service.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "US2: Loading of all clients", tags = "users")
    @GetMapping
    protected Page<UserInfoDto> getAll(Pageable pageable) {
        return service.getPage(pageable);
    }

    @Operation(summary = "US3: Loading of client by uuid", tags = "users")
    @GetMapping(path = "/{uuid}")
    public UserInfoDto get(@PathVariable("uuid") UUID uuid) {
        log.info("get user with {}", uuid);
        return service.findById(uuid);
    }

    @Operation(summary = "US4: Update client by uuid", tags = "users")
    @PutMapping(path = "/{uuid}")
    public void update(@PathVariable("uuid") UUID uuid,
                       @Valid @RequestBody UserCreateDto user) {
        service.update(uuid, user);
        log.info("successfully update user with {}", uuid);
    }
}

