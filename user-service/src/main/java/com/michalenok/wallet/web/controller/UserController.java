package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.service.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    // this method creates user with all data. Used by Admin
    @RequestMapping(method = RequestMethod.POST)
    protected ResponseEntity<?> create(@RequestBody @Validated UserCreateDto user) {
        log.info("create " + user);
        service.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    // this method allows to get all users in the system. Used by Admin
    @RequestMapping(method = RequestMethod.GET)
    protected ResponseEntity<Page<UserInfoDto>> getAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "20") Integer size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getPage(paging));
    }
    // this method allows to get user by id. Used by all
    @RequestMapping(path = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<UserInfoDto> get(@PathVariable("uuid") UUID uuid) {
        log.info("get user with " + uuid);
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findById(uuid));
    }
    // this method allows to update user data. Used by all
    @RequestMapping(path = "/{uuid}/dt_update/{dt_update}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable("uuid") UUID uuid,
                                    @PathVariable("dt_update") Instant dtUpdate,
                                    @RequestBody @Validated UserCreateDto user) {
        service.update(uuid, dtUpdate, user);
        log.info("successfully update user with " + uuid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

