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

    /**
     * this method creates user with all data. Used by Admin
     */
    @PostMapping
    protected ResponseEntity<?> create(@RequestBody @Validated UserCreateDto user) {
        log.info("create {}", user);
        service.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * this method allows to get all users in the system. Used by Admin
     */
    @GetMapping
    protected Page<UserInfoDto> getAll(Pageable pageable) {
        return service.getPage(pageable);
    }

    /**
     * this method allows to get user by id. Used by all
     */
    @GetMapping(path = "/{uuid}")
    public UserInfoDto get(@PathVariable("uuid") UUID uuid) {
        log.info("get user with {}", uuid);
        return service.findById(uuid);
    }

    /**
     * this method allows to update user data. Used by all
     */
    @PutMapping(path = "/{uuid}")
    public void update(@PathVariable("uuid") UUID uuid,
                       @Valid @RequestBody UserCreateDto user) {
        service.update(uuid, user);
        log.info("successfully update user with {}", uuid);
    }
}

