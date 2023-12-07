package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.service.api.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Tag(name = "users")
@Log4j2
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
  
    @Operation(summary = "Register client in application")
    @ApiResponses({
            @ApiResponse(responseCode="201", description ="Created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    protected ResponseEntity<?> create(@RequestBody @Validated UserCreateDto user) {
        log.info("create {}", user);
        service.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Loading of all clients",
    security = @SecurityRequirement(name = "security_auth" ))
    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    protected Page<UserInfoDto> getAll(Pageable pageable) {
        return service.getPage(pageable);
    }
 
    @Operation(summary = "Loading of client by uuid")
    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/{uuid}")
    public UserInfoDto get(@PathVariable("uuid") UUID uuid) {
        log.info("get user with {}", uuid);
        return service.findById(uuid);
    } 
  
    @Operation(summary = "Update client by uuid")
    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{uuid}")
    public void update(@PathVariable("uuid") UUID uuid,
                       @Valid @RequestBody UserCreateDto user) {
        service.update(uuid, user);
        log.info("successfully update user with {}", uuid);
    }
}

