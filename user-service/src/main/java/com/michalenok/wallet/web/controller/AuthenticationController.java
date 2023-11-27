package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.request.UserLoginDto;
import com.michalenok.wallet.model.dto.request.UserRegistrationDto;
import com.michalenok.wallet.service.api.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class AuthenticationController {
    private final AuthenticationService service;

    @Operation(summary ="Register client", tags = "authorization",
            security = @SecurityRequirement(name = "security_auth"))
    @ApiResponses({
            @ApiResponse(responseCode="201", description ="Created", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping(path = "/registration")
    protected ResponseEntity<?> create(
            @RequestBody @Validated UserRegistrationDto user) {
        service.register(user);
        log.info("Registration of user with mail: {} is successful", user.mail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Verify client", tags = "authorization",
            security = @SecurityRequirement(name = "security_auth"))
    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @GetMapping(path = "/verification")
    protected void verify(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "mail") String mail) {
        service.verifyUser(code, mail);
        log.info("Authentication of user with mail: {} is successful", mail);
    }
  
    @Operation(summary = "Authenticate client", tags = "authorization",
            security = @SecurityRequirement(name = "security_auth"))
    @ApiResponses({
            @ApiResponse(responseCode="200", description ="Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Server Error")
    })
    @PostMapping(path = "/login")
    public void login(@RequestBody @Validated UserLoginDto user) {
        service.login(user);
        log.info("Authorization of {}", user.mail());
    }
}
