//package com.michalenok.wallet.integration;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.github.tomakehurst.wiremock.junit5.WireMockTest;
//import com.michalenok.wallet.feign.AccountServiceFeignClient;
//import com.michalenok.wallet.keycloak.KeycloakService;
//import com.michalenok.wallet.model.constant.UserStatus;
//import com.michalenok.wallet.model.dto.request.UserCreateDto;
//import com.michalenok.wallet.model.dto.request.UserRegistrationDto;
//import com.michalenok.wallet.model.exception.UserAlreadyExistException;
//import com.michalenok.wallet.model.exception.VerificationUserException;
//import com.michalenok.wallet.service.api.AuthenticationService;
//import com.michalenok.wallet.service.api.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.UUID;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@WireMockTest(httpPort = 8180)
//class AuthenticationServiceIntegrationImplTest extends IntegrationTestBase {
//    @Autowired
//    private AuthenticationService authenticationService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private AccountServiceFeignClient accountServiceFeignClient;
//    @MockBean
//    private KeycloakService keycloakService;
//
//    @Test
//    void verifyUser_Successful() {
//        String testMail = "testuaser1@test.com";
//        authenticationService.verifyUser("ad16c459-386f-427b-a5e5-763577425e5d", testMail);
//        assertEquals(UserStatus.ACTIVATED, userService.findByMail(testMail).status());
//    }
//
//    @Test
//    void verifyUser_VerificationUserException() {
//        assertThrows(VerificationUserException.class, () -> authenticationService.verifyUser(UUID.randomUUID().toString(),
//                "dmitryd@dmitry.com"));
//    }
//
//    @Test
//    void registerUser_Successful() {
//        String mail = "testuser10@test.com";
//        when(keycloakService.addUser(any(UserCreateDto.class)))
//                .thenReturn(UUID.randomUUID().toString());
//        authenticationService.register(UserRegistrationDto.builder()
//                .mail(mail)
//                .mobilePhone("98765411")
//                .password("12345678")
//                .build());
//        assertNotNull(userService.findByMail(mail));
//        assertEquals(UserStatus.WAITING_ACTIVATION,userService.findByMail(mail).status());
//    }
//
//    @Test
//    void registerUser_VerificationUserException() {
//        String testMail = "testuaser1@test.com";
//        assertThrows(UserAlreadyExistException.class, () ->
//                authenticationService.register(UserRegistrationDto.builder()
//                .mail(testMail)
//                .mobilePhone("98765411")
//                .password("12345678")
//                .build()));
//    }
//}