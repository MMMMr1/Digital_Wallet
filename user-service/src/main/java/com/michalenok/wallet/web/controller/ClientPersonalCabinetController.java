package com.michalenok.wallet.web.controller;

import com.michalenok.wallet.configuration.JwtUtil;
import com.michalenok.wallet.model.dto.request.UserCreateDto;
import com.michalenok.wallet.model.dto.response.UserInfoDto;
import com.michalenok.wallet.service.api.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Tag(name = "personal cabinet")
@Log4j2
@RestController
@RequestMapping("/api/v1/users/details")
@RequiredArgsConstructor
public class ClientPersonalCabinetController {
    private final UserService service;
    private final JwtUtil jwtUtil;
    @Operation(summary = "Show personal client data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "ServerError")
    })
    @GetMapping
    public UserInfoDto getDetails(@AuthenticationPrincipal Jwt jwt) {
        return service.findByMail(jwtUtil.getPrincipleClaimName(jwt));
    }

    @Operation(summary = "Update client data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "ServerError")
    })
    @PutMapping
    public void update(@AuthenticationPrincipal Jwt jwt,
                     @Valid @RequestBody UserCreateDto user) {
        service.update(jwtUtil.getPrincipleClaimName(jwt), user);
    }
}

