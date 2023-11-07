package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.request.UserLoginDto;
import com.michalenok.wallet.model.dto.request.UserRegistrationDto;
import com.michalenok.wallet.service.api.AuthenticationService;
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

    @PostMapping(path = "/registration")
    protected ResponseEntity<?> create(@RequestBody @Validated UserRegistrationDto user) {
        service.register(user);
        log.info("Registration of user with mail: {} is successful", user.mail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/verification")
    protected void verify(@RequestParam(name = "code") String code,
                          @RequestParam(name = "mail") String mail) {
        service.verifyUser(code, mail);
        log.info("Authentication of user with mail: {} is successful", mail);
    }

    @PostMapping(path = "/login")
    public void login(@RequestBody @Validated UserLoginDto user) {
        service.login(user);
        log.info("Authorization of {}", user.mail());
    }
}
