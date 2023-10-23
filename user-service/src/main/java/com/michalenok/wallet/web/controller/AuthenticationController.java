package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.model.dto.request.UserLoginDto;
import com.michalenok.wallet.model.dto.request.UserRegistrationDto;
import com.michalenok.wallet.service.api.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class AuthenticationController {
    private final AuthenticationService service;

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    protected ResponseEntity<?> create(
            @RequestBody @Validated UserRegistrationDto user) {
        service.register(user);
        log.info("Registration of user with mail: "+ user.getMail()+ "is successful");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @RequestMapping( path = "/verification", method = RequestMethod.GET)
    protected ResponseEntity<?> verify(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "mail") String mail)  {
        service.verify(code,mail);
        log.info("Authentication of user with mail: "+ mail+ "is successful");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody @Validated UserLoginDto user) {
        service.login(user);
        log.info("Authorization of "+ user+ "is successful" );
        return ResponseEntity.status(HttpStatus.OK).build();
    }
//    @RequestMapping(path = "/details", method = RequestMethod.GET)
//    public ResponseEntity<?>  getUserInfo() {
//        UserHolder userHolder = new  UserHolder();
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(userHolder.getUser());
//    }
}
