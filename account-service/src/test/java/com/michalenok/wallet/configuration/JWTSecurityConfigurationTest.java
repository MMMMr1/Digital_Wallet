package com.michalenok.wallet.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michalenok.wallet.service.api.AccountService;
import com.michalenok.wallet.web.controller.AccountController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static java.util.UUID.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {SecurityConfiguration.class,
        AccountController.class
})
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JWTSecurityConfigurationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService service;
    private String uuid;

    @BeforeAll
    public void init() {
        uuid = randomUUID().toString();
    }

    @Test
    public void givenRequestNotAuthorized_whenCreateAccount_thenUnauthorized() throws Exception {
        mockMvc.perform(post("/api/v1/accounts")
                        .header("Is-Proxy-Request", true)
                        .param("client_uuid", uuid))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void givenRequestAuthorized_whenCreateAccount_thenCreated() throws Exception {
        mockMvc.perform(post("/api/v1/accounts")
                        .header("Is-Proxy-Request", true)
                        .param("client_uuid", uuid))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenRequestNotAuthorized_whenGetAllAccounts_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/accounts")
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenRequestAuthorizedAsUser_whenGetAllAccounts_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/accounts")
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRequestAuthorizedAsAdmin_whenGetAllAccounts_thenCreated() throws Exception {
        mockMvc.perform(get("/api/v1/accounts")
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isOk());
    }

    @Test
    public void givenRequestNotAuthorized_whenGet_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/details")
                        .header("Is-Proxy-Request", true)
                        .param("account_uuid", uuid))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenRequestAuthorizedAsUser_whenGet_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/details")
                        .header("Is-Proxy-Request", true)
                        .param("account_uuid", uuid))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRequestAuthorizedAsAdmin_whenGet_thenCreated() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/details")
                        .header("Is-Proxy-Request", true)
                        .param("account_uuid", uuid))
                .andExpect(status().isOk());
    }

    @Test
    public void givenRequestNotAuthorized_whenGetAllAccountsByClientId_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/{uuid}", uuid)
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenRequestAuthorizedAsUser_whenGetAllAccountsByClientId_thenForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/{uuid}", uuid)
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRequestAuthorizedAsAdmin_whenGetAllAccountsByClientId_thenOk() throws Exception {
        mockMvc.perform(get("/api/v1/accounts/{uuid}", uuid)
                        .header("Is-Proxy-Request", true))
                .andExpect(status().isOk());
    }

    @Test
    public void givenRequestNotAuthorized_whenCloseAccount_thenUnauthorized() throws Exception {
        mockMvc.perform(put("/api/v1/accounts/closing")
                        .header("Is-Proxy-Request", true)
                        .param("account_uuid", uuid))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void givenRequestAuthorizedAsUser_whenCloseAccount_thenForbidden() throws Exception {
        mockMvc.perform(put("/api/v1/accounts/closing")
                        .header("Is-Proxy-Request", true)
                        .param("account_uuid", uuid))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void givenRequestAuthorizedAsAdmin_whenCloseAccount_thenCreated() throws Exception {
        mockMvc.perform(put("/api/v1/accounts/closing")
                        .header("Is-Proxy-Request", true)
                        .param("account_uuid", uuid))
                .andExpect(status().isOk());
    }
}